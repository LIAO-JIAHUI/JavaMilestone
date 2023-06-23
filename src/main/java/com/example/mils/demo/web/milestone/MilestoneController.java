package com.example.mils.demo.web.milestone;

import com.example.mils.demo.domain.milestone.MilestoneEntity;
import com.example.mils.demo.domain.milestone.MilestoneService;
import com.example.mils.demo.web.user.UserGlobalEntity;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.BeanUtils;

@Controller
@AllArgsConstructor
@RequestMapping("/milestones")
public class MilestoneController {
    private final MilestoneService milestoneService; // MilestoneServiceインスタンスの生成

    /**
     * showList
     * 全てのマイルストーンをSQLで取得してリストページに表示
     * 
     * @param model
     * @return
     */
    @GetMapping
    public String showList(Model model) {
        model.addAttribute("milestoneList", milestoneService.findAll());
        return "milestones/list";
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
     */
    @PostMapping("/create") // POSTリクエストのアノテーション
    public String create(@Validated MilestoneForm milestoneForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "milestones/form";
        } else {
            milestoneService.create(
                    ((UserGlobalEntity) model.getAttribute("userHash")).getUsername(),
                    milestoneForm.getTitle(),
                    milestoneForm.getDescription(),
                    milestoneForm.getStatus(),
                    milestoneForm.getScheduleAt(),
                    milestoneForm.getDeadlineAt());
            return "redirect:/milestones";
        }
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
     * 編集ページへ遷移
     * 編集が成功したらDBにupdate
     * 
     * @param id
     * @param milestoneForm
     * @param bindingResult
     * @param model
     * @return
     */
    @PutMapping("/{id}")
    public String edit(@PathVariable("id") String id, @Validated MilestoneForm milestoneForm,
            BindingResult bindingResult, Model model) {

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
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id, Model model) {
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

        milestoneService.deleteById(longId);
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
}
