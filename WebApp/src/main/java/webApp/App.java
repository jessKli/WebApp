package webApp;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import bank.BankService;
import bank.Constants;
import bank.Customer;

public class App {
	private static  BankService bankService;
	public static void main(String []args){
		System.out.println("Running from App.java");
		ConfigurableApplicationContext ctx=SpringApplication.run(BankService.class, args);
		bankService = (BankService) ctx.getBean("bankService");
		showMenuOptions();
		ctx.close();
		
	}
	public static void showMenuOptions() {
		boolean exit = false;
		int input;
		Customer cust=new Customer();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Press:\n1 for create new customer\n2 for login");		
		input=scanner.nextInt();
		if(input==Constants.CREATE_NEW_CUSTOMER) {
			cust=bankService.getCreateNewCustomer();
		}else if(input==Constants.LOGIN){
			cust=bankService.getGetCustomerByIdAndPwd();
		}
		if(cust==null) {
			exit=true;
		}
		while (!exit) {
			System.out.println(
					"How can we help you?\nPress:"
					+ "\n3 for stop being a client in this bank"
					+ "\n4 for create account"
					+ "\n5 for delete account"
					+ "\n6 for adjust account"
					+ "\n7 for all my transactions"
					+ "\n9 to exit");
			input = scanner.nextInt();
			switch (input) {
			case Constants.DELETE_CUSTOMER:
				if(bankService.getDeleteCustomer(cust)) {
					exit=true;
				}
				break;
			case Constants.DELETE_ACCOUNT:
				bankService.getDeleteAccount(cust);
				break;
			case Constants.CREATE_ACCOUNT:
				bankService.getCreateAccount(cust);
				break;
			case Constants.ADJUST_ACCOUNT:
				bankService.getAdjustTheAmountOfTheAccount(cust);
				break;
			case Constants.MY_TRANSACTIONS:
				bankService.getPrintCustomerTransactions(cust);
				break;
			case Constants.EXIT:
				BankService.getExitMsg();
				exit = true;
				break;
			default:
				System.out.println("What do you want to do");
				break;
			}
		}
	}

	
}
