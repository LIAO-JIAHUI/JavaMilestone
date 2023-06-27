package com.example.mils.demo.web.milestone;

import com.example.mils.demo.FirebaseMessagingService;
import com.example.mils.demo.Note;
import com.example.mils.demo.domain.milestone.MilestoneEntity;
import com.example.mils.demo.domain.milestone.MilestoneService;
import com.example.mils.demo.domain.userToken.UserTokenEntity;
import com.example.mils.demo.domain.userToken.UserTokenService;
import com.example.mils.demo.web.pushMessage.Notification;
import com.example.mils.demo.web.user.UserGlobalEntity;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Controller
@AllArgsConstructor
@RequestMapping("/milestones")
public class MilestoneController {
    private final MilestoneService milestoneService; // MilestoneServiceインスタンスの生成
    private final FirebaseMessagingService firebaseMessagingService;
    private final UserTokenService userTokenService;
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * showList
     * 全てのマイルストーンをSQLで取得してリストページに表示
     * フィルタ、検索、並び替え
     * 
     * @param model
     * @return
     */
    @GetMapping
    public String showList(
            Model model,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String orderBy,
            @RequestParam(required = false) String order) {

        model.addAttribute("title", title);
        model.addAttribute("author", author);
        model.addAttribute("status", status);
        model.addAttribute("orderBy", orderBy);
        model.addAttribute("order", order);
        model.addAttribute("milestoneList", milestoneService.search(title, author, status, orderBy, order));
        model.addAttribute("completionRate", milestoneService.getCompletionRate("done") * 100 + " %");

        return "milestones/list";
    }

    @GetMapping("/gantt")
    public String showGanttChart(Model model) {
        model.addAttribute("milestoneList", milestoneService.search(null, null, null, null, null));
        return "milestones/gantt";
    }

    /**
     * showCreationForm
     * 作成ページへ遷移
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
     * create
     * 作成ページからのpostを受け取り
     * 新しいマイルストーンをinsert
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
        }
        UserGlobalEntity user = (UserGlobalEntity) model.getAttribute("userHash");
        milestoneService.create(
                user.getUsername(),
                milestoneForm.getTitle(),
                milestoneForm.getDescription(),
                milestoneForm.getStatus(),
                milestoneForm.getScheduleAt(),
                milestoneForm.getDeadlineAt());
        messagingTemplate.convertAndSend("/all/messages",
                new Notification(null, milestoneForm.getTitle(), "create", null, getUser(model)));

        List<UserTokenEntity> userTokens = userTokenService.findAll();
        Note note = new Note("お知らせ", "マイルストーンが新しく作成されました", "/milestones");
        for (int i = 0; i < userTokens.size(); i++) {
            firebaseMessagingService.send(note, userTokens.get(i).getToken());
        }

        return "redirect:/milestones";
    }

    /**
     * showDetail
     * 詳細ページへ遷移
     * ルーティングのidを用いてsqlで一件取得
     * idはString入力でlong型に変換
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
     * showEditForm
     * 詳細ページから編集ページへ遷移
     * idからMilestoneEntity取得し
     * MilestoneFormへ必要な情報を入れてHTMLに渡す
     * 
     * @param milestoneForm
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(
            @ModelAttribute MilestoneForm milestoneForm, @PathVariable("id") String id, Model model) {
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
     * edit
     * 編集ページ
     * 編集が成功したらDBにupdate
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
        milestoneService.update(longId, milestoneForm.getTitle(),
                milestoneForm.getDescription(),
                milestoneForm.getStatus(), milestoneForm.getScheduleAt(),
                milestoneForm.getDeadlineAt());

        messagingTemplate.convertAndSend("/all/messages",
                new Notification(id, milestoneForm.getTitle(), "edit", null, getUser(model)));

        List<UserTokenEntity> userTokens = userTokenService.findAll();
        Note note = new Note("お知らせ", "マイルストーンが編集されました", "/milestones/" + milestone.getId());
        for (int i = 0; i < userTokens.size(); i++) {
            firebaseMessagingService.send(note, userTokens.get(i).getToken());
        }

        return "redirect:/milestones/{id}";
    }

    /**
     * delete
     * 削除アクション
     * 成功した場合DBにdelete
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

        messagingTemplate.convertAndSend("/all/messages",
                new Notification(id, milestoneService.getTitleById(longId), "delete", null, getUser(model)));
        milestoneService.deleteById(longId);

        List<UserTokenEntity> userTokens = userTokenService.findAll();
        Note note = new Note("お知らせ", "マイルストーンが削除されました", "/milestones");
        for (int i = 0; i < userTokens.size(); i++) {
            firebaseMessagingService.send(note, userTokens.get(i).getToken());
        }

        return "redirect:/milestones";
    }

    /**
     * isNotAuthor
     * 作成者であるか確認する
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
