package com.example.mils.demo.web.milestone;

import com.example.mils.demo.FirebaseMessagingService;
import com.example.mils.demo.Note;
import com.example.mils.demo.domain.milestone.MilestoneEntity;
import com.example.mils.demo.domain.milestone.MilestoneService;
import com.example.mils.demo.web.pushMessage.MessageController;
import com.example.mils.demo.domain.userToken.UserTokenEntity;
import com.example.mils.demo.domain.userToken.UserTokenService;
import com.example.mils.demo.web.pushMessage.Notification;
import com.example.mils.demo.web.user.UserGlobalEntity;
import com.example.mils.demo.web.user.UserService;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.google.firebase.messaging.FirebaseMessagingException;

import java.util.List;

import org.springframework.beans.BeanUtils;

@Controller
@AllArgsConstructor
@RequestMapping("/milestones")
public class MilestoneController {
    private final MilestoneService milestoneService; // MilestoneServiceインスタンスの生成
    private final UserService userService;
    private final MessageController MessageController;
    private final FirebaseMessagingService firebaseMessagingService;
    private final UserTokenService userTokenService;

    /**
     * showList 全てのマイルストーンをSQLで取得してリストページに表示 フィルタ、検索、並び替え
     * 
     * @param model
     * @return
     */
    @GetMapping
    public String showList(Model model, @RequestParam(required = false) String title,
            @RequestParam(required = false) String author, @RequestParam(required = false) String status,
            @RequestParam(required = false) String orderBy, @RequestParam(required = false) String order) {

        UserGlobalEntity user = (UserGlobalEntity) model.getAttribute("userHash");

        model.addAttribute("title", title);
        model.addAttribute("author", author);
        model.addAttribute("status", status);
        model.addAttribute("orderBy", orderBy);
        model.addAttribute("order", order);
        List<MilestoneEntity> milestoneList = milestoneService.search(title, author, status, orderBy, order,
                user.getUsername());
        model.addAttribute("milestoneList", milestoneList);
        model.addAttribute("completionRate", milestoneService.getCompletionRate("done") * 100 + " %");

        return "milestones/list";
    }

    @GetMapping("/gantt")
    public String showGanttChart(Model model) {
        UserGlobalEntity user = (UserGlobalEntity) model.getAttribute("userHash");

        model.addAttribute("milestoneList", milestoneService.search(null, null, null, null, null, user.getUsername()));
        return "milestones/gantt";
    }

    /**
     * showCreationForm 作成ページへ遷移
     * 
     * @param milestoneForm
     * @return
     */
    @GetMapping("/create")
    public String showCreationForm(@ModelAttribute MilestoneForm milestoneForm,
            @RequestParam(required = false) String duplicateId) {
        Long longDuplicateId = null;
        if (duplicateId != null) {
            try {
                longDuplicateId = Long.valueOf(duplicateId);
            } catch (NumberFormatException e) {
                return "redirect:/milestones";
            }

            MilestoneEntity duplicateMilestone = milestoneService.getById(longDuplicateId);
            if (duplicateMilestone != null) {
                BeanUtils.copyProperties(duplicateMilestone, milestoneForm);
            }
        }

        return "milestones/form";
    }

    /**
     * create 作成ページからのpostを受け取り 新しいマイルストーンをinsert
     * 
     * @param milestoneForm
     * @param bindingResult
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/create") // POSTリクエストのアノテーション
    public String create(@Validated MilestoneForm milestoneForm, BindingResult bindingResult, Model model)
            throws Exception {
        if (bindingResult.hasErrors()) {
            return "milestones/form";
        } else {
            UserGlobalEntity user = (UserGlobalEntity) model.getAttribute("userHash");
            MilestoneEntity milestoneEntity = milestoneService.createByEntity(new MilestoneEntity(0, user.getUsername(),
                    Long.valueOf(milestoneForm.getGroupId()), milestoneForm.getGroupId(), milestoneForm.getTitle(),
                    milestoneForm.getDescription(), milestoneForm.getStatus(), milestoneForm.getScheduleAt(),
                    milestoneForm.getDeadlineAt(), null, null));
            long insertId = milestoneEntity.getId();
            model.addAttribute("insertId", insertId);
            // ブラウザ通知
            MessageController.sendToSpecificUser(new Notification(String.valueOf(insertId), milestoneForm.getTitle(),
                    "create", userService.getUserListByGroup(Long.parseLong(milestoneForm.getGroupId())),
                    getUser(model)));
            // firebase通知
            List<UserTokenEntity> userTokens = userTokenService.whereNotAll(getUser(model));
            Note note = new Note("お知らせ", user.getUsername() + "が「" + milestoneEntity.getTitle() + "」を作成しました",
                    "/milestones/" + insertId);
            try {
                for (int i = 0; i < userTokens.size(); i++) {
                    firebaseMessagingService.send(note, userTokens.get(i).getToken());
                }
            } catch (FirebaseMessagingException e) {
                System.out.println(e);
            }
            return "redirect:/milestones/" + String.valueOf(insertId);
        }
    }

    /**
     * showDetail 詳細ページへ遷移 ルーティングのidを用いてsqlで一件取得 idはString入力でlong型に変換
     * 
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{id}")
    public String showDetail(@PathVariable("id") String id, Model model) {
        Long longId = null;
        try {
            longId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            return "redirect:/milestones";
        }

        MilestoneEntity milestone = milestoneService.getById(longId);
        if (milestone == null) {
            return "redirect:/milestones";
        }
        model.addAttribute("milestone", milestone);
        return "milestones/detail";
    }

    /**
     * showEditForm 詳細ページから編集ページへ遷移 idからMilestoneEntity取得し
     * MilestoneFormへ必要な情報を入れてHTMLに渡す
     * 
     * @param milestoneForm
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(@ModelAttribute MilestoneForm milestoneForm, @PathVariable("id") String id,
            Model model) {
        Long longId = null;
        try {
            longId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            return "redirect:/milestones";
        }

        MilestoneEntity milestone = milestoneService.getById(longId);
        if (milestone == null || isNotAuthor(model, milestone)) {
            return "redirect:/milestones";
        }
        BeanUtils.copyProperties(milestone, milestoneForm);
        model.addAttribute("id", longId);
        return "milestones/form";
    }

    /**
     * edit 編集ページ 編集が成功したらDBにupdate
     * 
     * @param id
     * @param milestoneForm
     * @param bindingResult
     * @param model
     * @return
     * @throws Exception
     */
    @PutMapping("/{id}")
    public String edit(@PathVariable("id") String id, @Validated MilestoneForm milestoneForm,
            BindingResult bindingResult, Model model) throws Exception {
        Long longId = null;
        try {
            longId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            return "redirect:/milestones";
        }
        if (bindingResult.hasErrors()) {
            return "milestones/form";
        }
        MilestoneEntity milestone = milestoneService.getById(longId);
        if (milestone == null || isNotAuthor(model, milestone)) {
            return "redirect:/milestones";
        }
        milestoneService.update(longId, milestoneForm.getTitle(), milestoneForm.getDescription(),
                milestoneForm.getStatus(), milestoneForm.getScheduleAt(), milestoneForm.getDeadlineAt());
        // ブラウザ通知
        MessageController.sendToSpecificUser(new Notification(id, milestoneForm.getTitle(), "edit",
                userService.getUserListByGroup(milestone.getGroup_id()), getUser(model)));
        // firebase通知
        List<UserTokenEntity> userTokens = userTokenService.whereNotAll(getUser(model));
        Note note = new Note("お知らせ", getUser(model) + "が「" + milestone.getTitle() + "」を編集しました",
                "/milestones/" + milestone.getId());
        for (int i = 0; i < userTokens.size(); i++) {
            firebaseMessagingService.send(note, userTokens.get(i).getToken());
        }

        return "redirect:/milestones/{id}";
    }

    /**
     * delete 削除アクション 成功した場合DBにdelete
     * 
     * @param id
     * @param model
     * @return
     * @throws Exception
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id, Model model) throws Exception {
        Long longId = null;
        try {
            longId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            return "redirect:/milestones";
        }

        MilestoneEntity milestone = milestoneService.getById(longId);
        if (milestone == null || isNotAuthor(model, milestone)) {
            return "redirect:/milestones";
        }

        MessageController.sendToSpecificUser(new Notification(null, milestoneService.getTitleById(longId), "delete",
                userService.getUserListByGroup(milestone.getGroup_id()), getUser(model)));
        milestoneService.deleteById(longId);

        List<UserTokenEntity> userTokens = userTokenService.whereNotAll(getUser(model));
        Note note = new Note("お知らせ", getUser(model) + "が「" + milestone.getTitle() + "」を削除しました", "/milestones");
        for (int i = 0; i < userTokens.size(); i++) {
            firebaseMessagingService.send(note, userTokens.get(i).getToken());
        }

        return "redirect:/milestones";
    }

    /**
     * isNotAuthor 作成者であるか確認する
     * 
     * @param model
     * @param milestone
     * @return
     */
    private boolean isNotAuthor(Model model, MilestoneEntity milestone) {
        return !(((UserGlobalEntity) model.getAttribute("userHash")).getUsername()).equals(milestone.getAuthor());
    }

    private String getUser(Model model) {
        UserGlobalEntity user = (UserGlobalEntity) (model.getAttribute("userHash"));
        return user.getUsername();
    }
}
