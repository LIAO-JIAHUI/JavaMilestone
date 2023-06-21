package com.example.mils.demo.web.milestone;

import com.example.mils.demo.domain.milestone.MilestoneEntity;
import com.example.mils.demo.domain.milestone.MilestoneService;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
@AllArgsConstructor
@RequestMapping("/milestones")
public class MilestoneController {
    private final MilestoneService milestoneService; // MilestoneServiceインスタンスの生成

    // GET (localhost:8080/milestones でアクセスできるようにする)
    // @GetMapping("/milestones")
    // showList(model[spring dataのモデルでModel型])を受け取る
    // (Javaのデータをthymeleafに渡す際にSpring data の Model を利用する)
    // (ハンドラーメソッドでModelを呼び出すとSpringからメソッド生成時にmodelのインスタンスを受け取れる)
    // Listを用いてmilestoneList[var/型推論]を作成、3つほどnew演算子でMilestoneEntityインスタンスを生成する(引数はMilestoneEntityを参照)

    @GetMapping
    public String showList(Model model) {
        // thymleafにmodelのオブジェクトを渡す
        // 第１引数は、thymeleafが参照するキーを記入、第２引数は、thymeleafに渡すオブジェクト(List)を指定する
        // ここでは、第一引数は”milestoneList”とする
        // 返り値としてview名を渡す。今回は milestones/listを準備する
        model.addAttribute("milestoneList", milestoneService.findAll());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                // 現在のユーザー情報を使用して何か処理を行う
                String username = userDetails.getUsername();
                model.addAttribute("username", username);
            }
        }
        return "milestones/list";
    }

    @GetMapping("/createForm")
    public String showCreationForm(@ModelAttribute MilestoneForm form) {
        return "milestones/createForm";
    }

    @PostMapping("/createForm") // POSTリクエストのアノテーション
    public String create(@Validated MilestoneForm milestonForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // return showCreationForm(milestonForm);
            return "milestones/createForm";
        } else {
            milestoneService.create(milestonForm.getTitle(), milestonForm.getDescription());
            return "redirect:/milestones";
        }
    }

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
}