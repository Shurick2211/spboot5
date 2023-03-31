package com.sn.org.spboot5.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/telega")
public class TelegramWebApp {

  @GetMapping
  public String index(){
    return "/index";
  }

}
