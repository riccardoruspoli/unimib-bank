package it.unimib.bank.navigation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavigationController {

	@GetMapping("/")
	public String home(Model model) {
		return "index.html";
	}

	@GetMapping("/transfer")
	public String transfer(Model model) {
		return "transfer.html";
	}
}
