//javac AdvList.java
//jar cfmv AdvList.jar Manifest.txt AdvList.class
//java -jar AdvList.jar


import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.lang.StringBuilder;

public class insuranceDatabase{
    static boolean flag = true;
    static boolean flag2 = true;
    static Scanner input = new Scanner(System.in);
    static String userId;
    static String userPassword;
    static int custIDBASE = 10050;
    static int agentIDBASE = 50;
    static int policyIDBASE = 50;
    static LocalDate currentDate = java.time.LocalDate.now();
    static String localDate;
    static String localDate2 = "";
    static int claimIDBASE = 1000000010;
    static String pass;
public static void main(String[] args){
System.out.println("*********************************\n");
System.out.println("Welcome to the Insurance Database\n");
System.out.println("*********************************\n");
localDate = currentDate.toString();

System.out.println("Today's date is: " + localDate + "\n");
String[] parseDate = localDate.split("-");
int count = 0;
localDate2 = parseDate[0];
//System.out.println("Today's date is: " + localDate2 + "\n");
testConnection();

while(flag2){
mainMenu();
int mainMenuSelection = oneTovar(4);

switch(mainMenuSelection){
    case 1:
    corporateInterface();
    break;
    case 2:
    customerInterface();
    break;
    case 3:
    agentInterface();
    break;
    case 4:
    System.out.println("Exiting the program....");
    flag2 = false;
    break;
}

}


}





public static int oneOrzero(){
    int selection = 50;
    String temp;
    boolean check = true;
    boolean isInt = true;
    while(check){
    System.out.print("Selection = ");
    temp = input.nextLine();
    try{
        selection = Integer.parseInt(temp);
        isInt = true;
    }
    catch(NumberFormatException e){
        System.out.println("You must enter an Integer Value");
        isInt = false;
    }

if(isInt){
    if(selection == 1 || selection == 0){
        check = false;
    } else{
        check = true;
        System.out.println("Value must be 0 or 1");
    }
}

}
    return selection;
}
//END OF oneOrZero

public static int oneTovar(int end){
    int selection = 0;
    String temp;
    boolean check = true;
    boolean isInt = true;
    while(check){
    System.out.print("\nSelection = ");
    temp = input.nextLine();
    try{
        selection = Integer.parseInt(temp);
        isInt = true;
    }
    catch(NumberFormatException e){
        System.out.println("You must enter an Integer Value");
        isInt = false;
    }

if(isInt){
    if(selection > 0 && selection <= end){
        check = false;
    } else{
        check = true;
        System.out.println("Value must be between 1 and " + end);
    }
}

}
    return selection;
}
//END OF oneTovar

public static int intCheck(){
    int selection = 0;
    String temp;
    boolean check = true;
    boolean isInt = true;
    while(check){
    temp = input.nextLine();
    try{
        selection = Integer.parseInt(temp);
        isInt = true;
    }
    catch(NumberFormatException e){
        System.out.println("You must enter an Integer Value");
        isInt = false;
    }

if(isInt){
    if(selection > 0 ){
        check = false;
    } else{
        check = true;
        System.out.println("Value must be greater than 0");
    }
}

}
    return selection;
}
//END OF oneTovar

public static void mainMenu(){
System.out.println("---------------------------------------------");
System.out.println("1. Corporate Management Interface");
System.out.println("2. Customer Interface");
System.out.println("3. Agent Interface");
System.out.println("4. Exit the database");
}
//END OF mainMENU()

public static void customerInterface(){
    System.out.println("\n");
    System.out.println("Welcome to the Customer Interface");
    System.out.println("Are you a new or existing customer? ");
    System.out.println("0. New Customer");
    System.out.println("1. Existing Customer");
    int custStatus = oneOrzero();
    boolean custFlag = true;
while(custFlag){
    if(custStatus == 0){
        custIDBASE++;
    }
    try(Connection DBConnection1 = DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241",userId,pass);
        Statement stmt = DBConnection1.createStatement();)
        {
            DBConnection1.setAutoCommit(false);
            ResultSet rs;
            int custId = 10000;
            String custFirst = "";
            String custLast = "";

            if(custStatus == 0){
            custIDBASE++;
            System.out.print("Enter your first name: ");
            String firstName = input.nextLine();
            System.out.print("Enter your last name: ");
            String lastName = input.nextLine();
            custId= custIDBASE;
            int tempAgentID = agentIdGenerator();

            String findAgentQuery = "SELECT agent_firstName, agent_LastName FROM AGENT WHERE agent.agent_id = " + tempAgentID ;
            rs = stmt.executeQuery(findAgentQuery);
            String agentFirstName = "";
            String agentLastName = "";
            if(rs.next()){
            agentFirstName = rs.getString("agent_firstName");
            agentLastName = rs.getString("agent_lastName");
            }

            String agentInsert = "INSERT INTO AGENT VALUES ("+ tempAgentID + ", " + custId + ", '" + agentFirstName + "', '" + agentLastName +"')";
            stmt.executeUpdate(agentInsert);
            String custInsert = "INSERT INTO CUSTOMER VALUES (" + custId + ", '" + firstName + "', '" + lastName + "', " + tempAgentID +")";
            stmt.executeUpdate(custInsert);

            System.out.println("Your profile has been added to the database!");
            }

            if(custStatus ==1){


                int[] customers = new int[1000];
                        int counter1 = 0;
                        int customerCheck = 0;
                        String getCustomersQuery2 = "SELECT * FROM Customer";
                        rs = stmt.executeQuery(getCustomersQuery2);
                        System.out.println("List of all registered customers:\n");
                        while(rs.next()){
                        System.out.print("Customer ID: " +rs.getInt("customer_id"));
                        System.out.print(", First Name : " + rs.getString("customer_firstName"));
                        System.out.println(", Last Name: " + rs.getString("customer_lastName"));
                        customers[counter1] = rs.getInt("customer_id");
                        counter1 ++;
                }
                System.out.println("");
                
                boolean isnotCustomer = true;
                while(isnotCustomer){
                System.out.print("Enter your customer ID: ");
                custId = intCheck();
                //input.nextLine();
                for(int i =0 ; i< customers.length ; i++){
                    if (customers[i] == custId){
                        isnotCustomer = false;
                    }
                }
                if(isnotCustomer){
                System.out.println("Invalid customer ID. Try again");
                }
            }



            



            }

            boolean custMenuFlag = true;
            while(custMenuFlag){
            customerSubMenu();
            int custSubMenuSelection = oneTovar(6);
            switch(custSubMenuSelection){
                case 1:
                String findCustomerQuery = "SELECT * FROM CUSTOMER WHERE CUSTOMER.customer_id = " + custId;
                rs = stmt.executeQuery(findCustomerQuery);
                while(rs.next()){
                    System.out.print("ID: " +rs.getInt("customer_id"));
                    System.out.print(", First: " + rs.getString("customer_firstName"));
                    System.out.print(", Last: " + rs.getString("customer_lastName"));
                    System.out.println(", Agent ID: " + rs.getInt("agent_id"));
                }
                System.out.println("");
                break;
                case 2:
                boolean policyFlag = true;
                String agentGet = "Select* FROM agent WHERE agent.customer_id = " + custId;
                        rs = stmt.executeQuery(agentGet);
                        String agentFirstName = "";
                        String agentLastName = "";
                        int agentId = 0;
                        if(rs.next()){
                        agentId = rs.getInt("agent_id");
                        agentFirstName = rs.getString("agent_firstName");
                        agentLastName = rs.getString("agent_lastName");
                        }
                        
                while(policyFlag){
                    custPolicyMenu();
                    int policyChoice = oneTovar(9);
                    switch (policyChoice){
                        case 1:
                        int count1 = 0;
                        String getPoliciesQuery = "SELECT * FROM Policy WHERE POLICY.customer_id = " + custId;
                        rs = stmt.executeQuery(getPoliciesQuery);
                        while(rs.next()){
                        System.out.print("Policy ID: " +rs.getInt("policy_id"));
                        System.out.print(", Policy Date: " + rs.getString("policy_date"));
                        System.out.print(", Policy Price: " + rs.getInt("policy_price"));
                        System.out.println(", Agent ID: " + rs.getInt("agent_id"));
                        count1++;
                }
                System.out.println("");

                if(count1 == 0){
                    System.out.println("You have no policies\n");
                }
                        break;

                        case 2:
                        int count2 = 0;
                        String getAutoPoliciesQuery = "SELECT * FROM policy, Auto WHERE policy.customer_id = " + custId + " and auto.policy_id = policy.policy_id";
                        rs = stmt.executeQuery(getAutoPoliciesQuery);
                        /*
                        if(!rs.next()){
                            System.out.println("You have no auto policies.");
                        }
                        */
                        while(rs.next()){
                        System.out.print("Policy ID: " +rs.getInt("policy_id"));
                        System.out.print(", Policy Date: " + rs.getString("policy_date"));
                        System.out.print(", Policy Price: " + rs.getInt("policy_price"));
                        System.out.print(", Agent ID: " + rs.getInt("agent_id"));
                        System.out.print(", Vehicle Vin: " + rs.getString("vehicle_vin"));
                        System.out.print(", Vehicle Model: " + rs.getString("vehicleModel"));
                        System.out.print(", Vehicle Year: " + rs.getString("vehicleYear"));
                        System.out.print(", Primary Driver: " + rs.getString("primaryDriver"));
                        System.out.println(", Primary Location: " + rs.getString("primaryLocation"));
                        System.out.println("");
                        count2++;
                }
                if(count2 == 0){
                    System.out.println("You have no auto policies\n");
                }
                        break;
                        case 3:
                        int count3 = 0;
                        String getHomePoliciesQuery = "SELECT * FROM policy, Home WHERE policy.customer_id = " + custId + " and home.policy_id = policy.policy_id";
                        rs = stmt.executeQuery(getHomePoliciesQuery);
        
                        while(rs.next()){
                        System.out.print("Policy ID: " +rs.getInt("policy_id"));
                        System.out.print(", Policy Date: " + rs.getString("policy_date"));
                        System.out.print(", Policy Price: " + rs.getInt("policy_price"));
                        System.out.print(", Agent ID: " + rs.getInt("agent_id"));
                        System.out.print(", Property Location: " + rs.getString("propertyLocation"));
                        System.out.print(", House Size (sqr feet): " + rs.getString("houseSize"));
                        System.out.print(", House Value : " + rs.getString("houseValue"));
                        System.out.println(", House Type: " + rs.getString("houseType"));
                        System.out.println("");
                        count3++;
                }
                if(count3== 0){
                    System.out.println("You have no home policies\n");
                }
                        break;
                        case 4:
                        int count4 = 0;
                        String getLifePoliciesQuery = "SELECT * FROM policy, Life WHERE policy.customer_id = " + custId + " and life.policy_id = policy.policy_id";
                        rs = stmt.executeQuery(getLifePoliciesQuery);
        
                        while(rs.next()){
                        System.out.print("Policy ID: " +rs.getInt("policy_id"));
                        System.out.print(", Policy Date: " + rs.getString("policy_date"));
                        System.out.print(", Policy Price: " + rs.getInt("policy_price"));
                        System.out.print(", Agent ID: " + rs.getInt("agent_id"));
                        System.out.print(", Insured Person: " + rs.getString("insuredPerson"));
                        System.out.print(", Beneficiary: " + rs.getString("beneficiary"));
                        System.out.println(", Coverage Amount: " + rs.getString("lifeCoverage"));
                        System.out.println("");
                        count4++;
                        }
                        if(count4 == 0){
                            System.out.println("You have no life policies\n");
                        }
                        break;

                        case 5:
                        policyIDBASE++;
                        
                        //String date0 = "2021-05-12";
                        //java.sql.Date date1 = java.sql.Date.valueOf(date0);
                        
                        String policyId= Integer.toString(policyIDBASE);
                        //String agentPolicyUpdate = "INSERT INTO AGENT VALUES (" + randAgentID + ", " + custId + ", '" + agentFirstName +"' , '" + agentLastName +"')";
                        //stmt.executeUpdate(agentPolicyUpdate);
                        String PolicyInsert = "INSERT INTO POLICY VALUES ("+ policyId + ", " + localDate2 +" ,5500 , " + custId +", " + agentId +" , 'No')";
                        stmt.executeUpdate(PolicyInsert);
                        String autoPolicy_Type ="INSERT INTO POLICY_TYPE VALUES (" + policyId + ", 'Auto')";
                        stmt.executeUpdate(autoPolicy_Type);
                        
                        //input.nextLine();
                        System.out.print("Enter your vehicle VIN (Ex. SH23HBN4D8DV69MP2) : ");
                        String vehicleVin = input.nextLine();
                        System.out.print("Enter your vehicle Model (Ex. Mercedes) : ");
                        String vehicleModel = input.nextLine();
                        System.out.print("Enter your vehicle Year (Ex. 2019) : ");
                        String vehicleYear = input.nextLine();
                        System.out.print("Enter your vehicle primary driver (Ex. Potty Potts) : ");
                        String primaryDriver = input.nextLine();
                        System.out.print("Enter your vehicle primary location (Ex. Bethlehem PA) : ");
                        String vehicleLocation = input.nextLine();
                        System.out.print("Enter the price of your vehicle (Ex. 25000) : ");
                        int vehiclePrice = intCheck();

                        String autoPolicyInsert = "INSERT INTO AUTO VALUES (" + policyId + ", '" + vehicleVin + "', '" + vehicleModel + "', " + vehicleYear + ", '" + primaryDriver + "', '" + vehicleLocation + "', " + vehiclePrice + ")";
                        stmt.executeUpdate(autoPolicyInsert);

                        System.out.println("Auto policy added\n");
                        break;
                        case 6:
                        policyIDBASE++;
                        String policyId2= Integer.toString(policyIDBASE);
                        String PolicyInsertHome = "INSERT INTO POLICY VALUES ("+ policyId2 + ", " + localDate2 + ",8750 , " + custId +", " + agentId +" , 'No')";
                        stmt.executeUpdate(PolicyInsertHome);
                        String homePolicy_Type ="INSERT INTO POLICY_TYPE VALUES (" + policyId2 + ", 'Home')";
                        stmt.executeUpdate(homePolicy_Type);

                        //input.nextLine();
                        System.out.print("Enter your property location (Ex. Bethlehem PA) : ");
                        String properyLocation = input.nextLine();
                        System.out.print("Enter your house size (sqr feet) (Ex. 1000) : ");
                        int houseSize = intCheck();
                        System.out.print("Enter your house Value (Ex. 1000000) : ");
                        int houseValue= intCheck();
                        //input.nextLine();
                        System.out.print("Enter your house Type (Ex. RV) : ");
                        String houseType= input.nextLine();

                        String homePolicyInsert = "INSERT INTO HOME VALUES (" + policyId2 + ", '" + properyLocation + "', " + houseSize + ", " + houseValue + ", '" + houseType + "')";
                        stmt.executeUpdate(homePolicyInsert);
                        System.out.println("Home policy added\n");
                        break;
                        case 7:
                        policyIDBASE++;
                        String policyId3= Integer.toString(policyIDBASE);
                        String PolicyInsertLife = "INSERT INTO POLICY VALUES ("+ policyId3 + ", " + localDate2 + ",9750 , " + custId +", " + agentId +" , 'No')";
                        stmt.executeUpdate(PolicyInsertLife);
                        String lifePolicy_Type ="INSERT INTO POLICY_TYPE VALUES (" + policyId3 + ", 'Life')";
                        stmt.executeUpdate(lifePolicy_Type);

                        //input.nextLine();
                        System.out.print("Enter the name of the life you want to insure (Ex. Alex DAlessandro) : ");
                        String insuredPerson = input.nextLine();
                        System.out.print("Enter the beneficiary (Ex. Sally Potts) : ");
                        String beneficiary = input.nextLine();
                        

                        String lifePolicyInsert = "INSERT INTO lIFE VALUES (" + policyId3 + ", '" + insuredPerson + "', '" + beneficiary+ "', 10000)";
                        stmt.executeUpdate(lifePolicyInsert);
                        System.out.println("Life policy added\n");
                        break;



                        case 8:
                        int[] policies = new int[1000];
                        int counter = 0;
                        int removePolicy = 0;
                        String getPoliciesQuery2 = "SELECT * FROM Policy WHERE POLICY.customer_id = " + custId;
                        rs = stmt.executeQuery(getPoliciesQuery2);
                        System.out.println("List of your policies:\n");
                        while(rs.next()){
                        System.out.print("Policy ID: " +rs.getInt("policy_id"));
                        System.out.print(", Policy Date: " + rs.getString("policy_date"));
                        System.out.print(", Policy Price: " + rs.getInt("policy_price"));
                        System.out.println(", Agent ID: " + rs.getInt("agent_id"));
                        policies[counter] = rs.getInt("policy_id");
                        counter ++;
                }
                System.out.println("");

                if(counter == 0){
                    System.out.println("You have no policies\n");
                    break;
                }
                
                boolean isnotPolicy = true;
                while(isnotPolicy){
                System.out.print("Enter the policy ID of the policy you want to remove: ");
                removePolicy = intCheck();
                for(int i =0 ; i< policies.length ; i++){
                    if (policies[i] == removePolicy){
                        isnotPolicy = false;
                    }
                }
                if(isnotPolicy){
                System.out.println("Invalid policy ID. Try again");
                }
                }

                String checkType = "SELECT * FROM POLICY_TYPE WHERE POLICY_TYPE.policy_id = " +removePolicy;
                rs = stmt.executeQuery(checkType);
                String policyType = "";
                if(rs.next()){
                    policyType = rs.getString("policy_type");
                }
                System.out.println(policyType);
                        String deletePolicyCat = "DELETE FROM " + policyType + " WHERE " + policyType + ".policy_id = " + removePolicy;
                        stmt.executeUpdate(deletePolicyCat);

                        String deletePolicyType = "DELETE FROM POLICY_TYPE WHERE POLICY_TYPE.policy_id = " +removePolicy;
                        stmt.executeUpdate(deletePolicyType);

                        String deletePolicy = "DELETE FROM POLICY WHERE POLICY.policy_id = " +removePolicy;
                        stmt.executeUpdate(deletePolicy);

                        System.out.println("");
                        System.out.println("Policy ID: "+ removePolicy + " has been deleted");
                        break;
                        case 9:
                        policyFlag = false;
                        break;

                    }
                }
                break;
                case 3:
                boolean paymentFlag = true;
                while(paymentFlag){
                    System.out.println("1.View your payment method");
                    System.out.println("2.Add credit card");
                    System.out.println("3.Add debit card");
                    System.out.println("4.Add checking account");
                    System.out.println("5.Remove current payment method");
                    System.out.println("6.Return to customer interface");
                    int paymentSelection = oneTovar(6);
                    
                    switch(paymentSelection){
                        case 1:
                        String paymentType1 = "";
                        String getPaymentType = "SELECT * FROM PAYMENT_TYPE WHERE PAYMENT_TYPE.customer_id = " + custId;
                        rs = stmt.executeQuery(getPaymentType);
                        if(rs.next()){
                            paymentType1 = rs.getString("payment_name");
                        }



                        if(paymentType1.equals("Credit")){
                        String getCreditQuery = "SELECT * FROM CREDIT WHERE CREDIT.customer_id = " + custId;
                        rs = stmt.executeQuery(getCreditQuery);
                        while(rs.next()){
                        System.out.print("Customer ID: " +rs.getInt("customer_id"));
                        System.out.print(", Cardholder Name: " + rs.getString("cardholder_name"));
                        System.out.print(", Card Number: " + rs.getString("cardNumber"));
                        System.out.print(", Expiration Date: " + rs.getInt("cardExpdate"));
                        System.out.print(", Card CVC: " + rs.getInt("cardCvc"));
                        System.out.println(", Card Company: " + rs.getString("cardCompany"));
                }
                System.out.println("");
            }
                        


            if(paymentType1.equals("Debit")){
                String getCreditQuery = "SELECT * FROM DEBIT WHERE DEBIT.customer_id = " + custId;
                rs = stmt.executeQuery(getCreditQuery);
                while(rs.next()){
                System.out.print("Customer ID: " +rs.getInt("customer_id"));
                System.out.print(", Cardholder Name: " + rs.getString("cardholder_name"));
                System.out.print(", Card Number: " + rs.getString("cardNumber"));
                System.out.print(", Expiration Date: " + rs.getInt("cardExpdate"));
                System.out.print(", Card CVC: " + rs.getInt("cardCvc"));
                System.out.println(", Card Company: " + rs.getString("cardBank"));
        }
        System.out.println("");
    }

    if(paymentType1.equals("Checking")){
        String getCreditQuery = "SELECT * FROM CHECKING WHERE CHECKING.customer_id = " + custId;
        rs = stmt.executeQuery(getCreditQuery);
        while(rs.next()){
        System.out.print("Customer ID: " +rs.getInt("customer_id"));
        System.out.print(", Routing Number: " + rs.getString("routing_number"));
        System.out.println(", Account Number: " + rs.getString("account_number"));
}
System.out.println("");
}


                        break;

                        case 2:
                        try{
                        //input.nextLine();
                        System.out.print("Enter the cardholders name (Ex. Potty Potts) : ");
                        String cardholderName = input.nextLine();
                        System.out.print("Enter the card number (Ex. 9834675847364789) : ");
                        String cardNumber = input.nextLine();
                        System.out.print("Enter the card expiration date (Ex. 0825) : ");
                        int expDate = intCheck();
                        System.out.print("Enter the card cvc (Ex. 362) : ");
                        int cardCvc = intCheck();
                        //input.nextLine();
                        System.out.print("Enter the card company (Ex. Mastercard) : ");
                        String cardCompany = input.nextLine();
                        String creditPayType = "INSERT INTO PAYMENT_TYPE VALUES(" +custId + ", 'Credit')";
                        stmt.executeUpdate(creditPayType);

                        String creditInsert = "INSERT INTO CREDIT VALUES(" +custId + ", '" +cardholderName + "' , " + cardNumber + ", " + expDate + ", " + cardCvc + ", '" + cardCompany + "')";
                        stmt.executeUpdate(creditInsert);

                        System.out.println("New credit card added");
                       }catch(SQLException sql){
                            System.out.println("");
                            System.out.println("");
                           System.out.println("You can only have one payment method");
                            System.out.println("Remove one of your previous methods to add a new one\n");
                        }
                        break;

                        case 3:

                        //try{
                            //input.nextLine();
                            System.out.print("Enter the cardholders name (Ex. Potty Potts) : ");
                            String cardholderName = input.nextLine();
                            System.out.print("Enter the card number (Ex. 9834675847364789) : ");
                            String cardNumber = input.nextLine();
                            System.out.print("Enter the card expiration date (Ex. 0825) : ");
                            int expDate = intCheck();
                            System.out.print("Enter the card cvc (Ex. 362) : ");
                            int cardCvc = intCheck();
                            //input.nextLine();
                            System.out.print("Enter the card company (Ex. Mastercard) : ");
                            String cardCompany = input.nextLine();
                            String debitPayType = "INSERT INTO PAYMENT_TYPE VALUES(" +custId + ", 'Debit')";
                            stmt.executeUpdate(debitPayType);
    
                            String debitInsert = "INSERT INTO DEBIT VALUES(" +custId + ", '" +cardholderName + "' , " + cardNumber + ", " + expDate + ", " + cardCvc + ", '" + cardCompany + "')";
                            stmt.executeUpdate(debitInsert);
    
                            System.out.println("New debit card added");
                           // }catch(SQLException sql){
                                //System.out.println("");
                                //System.out.println("");
                                //System.out.println("You can only have one payment method");
                                //System.out.println("Remove one of your previous methods to add a new one\n");
                           // }


                        break;

                        case 4:

                       // try{
                            //input.nextLine();
                            System.out.print("Enter the routing number (Ex. 874635467) : ");
                            String routingNumber = input.nextLine();
                            System.out.print("Enter the account number (Ex. 874637564736) : ");
                            String accountNumber = input.nextLine();
                            

                            String checkingPayType = "INSERT INTO PAYMENT_TYPE VALUES(" +custId + ", 'Checking')";
                            stmt.executeUpdate(checkingPayType);
    
                            String checkingInsert = "INSERT INTO CHECKING VALUES(" +custId + ", " + routingNumber + ", " + accountNumber + ")";
                            stmt.executeUpdate(checkingInsert);
    
                            System.out.println("New checking account added");
                         //   }catch(SQLException sql){
                          //      System.out.println("");
                          //      System.out.println("");
                          //      System.out.println("You can only have one payment method");
                           //     System.out.println("Remove one of your previous methods to add a new one\n");
                           // }

                        break;

                        case 5:
                        
                        String paymentType2 = "";
                        String getPaymentType2 = "SELECT * FROM PAYMENT_TYPE WHERE PAYMENT_TYPE.customer_id = " + custId;
                        rs = stmt.executeQuery(getPaymentType2);
                        if(rs.next()){
                            paymentType2 = rs.getString("payment_name");
                        }

                        if(paymentType2.equals("Credit")){
                        
                        
                        String deleteCredit = "DELETE FROM CREDIT WHERE CREDIT.customer_id= " + custId;
                        stmt.executeUpdate(deleteCredit);

                        String deleteCreditType = "DELETE FROM PAYMENT_TYPE WHERE PAYMENT_TYPE.customer_id= " + custId;
                        stmt.executeUpdate(deleteCreditType);
                        System.out.println("Payment removed\n");
                        }

                        if(paymentType2.equals("Debit")){
                        
                        
                            String deleteDebit = "DELETE FROM DEBIT WHERE DEBIT.customer_id= " + custId;
                            stmt.executeUpdate(deleteDebit);
    
                            String deleteDebitType = "DELETE FROM PAYMENT_TYPE WHERE PAYMENT_TYPE.customer_id= " + custId;
                            stmt.executeUpdate(deleteDebitType);
                            System.out.println("Payment removed\n");
                            }

                        break;

                        case 6:
                        paymentFlag = false;
                        break;
                    }


                }

                break;
                case 5:
                System.out.println("Are you sure you want to delete your profile? Changes can not be reverted");
                System.out.println("0. No");
                System.out.println("1. Yes");
                int profileRemoval = oneOrzero();

                if(profileRemoval ==1){
                String deleteClaim = "DELETE FROM CLAIM WHERE CLAIM.customer_id = " + custId;
                stmt.executeUpdate(deleteClaim);
                System.out.println("Deleted from CLAIM");
                
                String deleteCustomer = "DELETE FROM CUSTOMER WHERE CUSTOMER.customer_id = " + custId;
                stmt.executeUpdate(deleteCustomer);
                System.out.println("Deleted from CUSTOMER");


                        int counter = 0;
                        int removePolicy = 0;
                        int[] policiesToDelete = new int[1000];
                        String getPoliciesQuery2 = "SELECT * FROM Policy WHERE POLICY.customer_id = " + custId;
                        rs = stmt.executeQuery(getPoliciesQuery2);
                        while(rs.next()){
                        policiesToDelete[counter] = rs.getInt("policy_id");
                        
                        counter ++;
                }

                for(int i = 0; i <counter; i++){
                removePolicy = policiesToDelete[i];

                String checkType = "SELECT * FROM POLICY_TYPE WHERE POLICY_TYPE.policy_id = " +removePolicy;
                rs = stmt.executeQuery(checkType);
                String policyType = "";
                if(rs.next()){
                    policyType = rs.getString("policy_type");
                }
                //System.out.println(policyType);
                        String deletePolicyCat = "DELETE FROM " + policyType + " WHERE " + policyType + ".policy_id = " + removePolicy;
                        stmt.executeUpdate(deletePolicyCat);

                        String deletePolicyType = "DELETE FROM POLICY_TYPE WHERE POLICY_TYPE.policy_id = " +removePolicy;
                        stmt.executeUpdate(deletePolicyType);

                        String deletePolicy = "DELETE FROM POLICY WHERE POLICY.policy_id = " +removePolicy;
                        stmt.executeUpdate(deletePolicy);

                        System.out.println("");
                        System.out.println("Policy ID: "+ removePolicy + " has been deleted");
            }


            

            //System.out.println(custId);
            String deleteCustomerAgent = "DELETE FROM AGENT WHERE AGENT.customer_id = " + custId;
            stmt.executeUpdate(deleteCustomerAgent);
            System.out.println("Deleted from AGENT");
        }else{
            System.out.println("Returning to menu...");
        }
                break;
                case 4:
                boolean claimFlag = true;
                while(claimFlag){
                    System.out.println("1. View claims");
                    System.out.println("2. Add a claim");
                    System.out.println("3. Remove a claim");
                    System.out.println("4. Return to customer interface");
                    int claimSelection = oneTovar(4);
                    switch(claimSelection){
                        case 1:
                        String customerClaims = "select * from claim where claim.customer_id = " + custId;

                         rs = stmt.executeQuery(customerClaims);

                        while(rs.next()){
                        System.out.print(" Claim ID: " + rs.getInt("claim_id"));
                        System.out.print(", Claim Description: " + rs.getString("claim_desc"));
                        System.out.print(", Policy ID: " + rs.getInt("policy_id"));
                        System.out.print(", Customer ID: " + rs.getInt("customer_id"));
                        System.out.print(", Adjustor ID: " + rs.getInt("adjustor_id"));
                        System.out.print(", Company ID: " + rs.getInt("company_id"));
                        System.out.print(", Claim Type: " + rs.getString("claim_type"));
                        System.out.println(", Claim Cost: " + rs.getInt("claim_cost"));
                        System.out.println("");
                        }
                        break;
                        case 2:
                        claimIDBASE++;
                        String claimInsert = "";
                        boolean isnotClaim1 = true;
                        String claim_Type = "";
                        while(isnotClaim1){
                        System.out.print("Enter a claim type (Ex. Auto/Home/Life) : ");
                        claim_Type = input.nextLine();
                        if(claim_Type.equals("Auto") || claim_Type.equals("Home") || claim_Type.equals("Life")){
                            isnotClaim1 = false;
                        } else{
                            System.out.println("You did not enter a valid claim type. Correct options: Auto/Home/Life\n");
                        }
                        }
                        System.out.print("Enter a claim description (Ex: Engine Broke) : ");
                        String claimDesc = input.nextLine();


                        int[] policies = new int[1000];
                        int counter = 0;
                        int policyIdCheck = 0;
                        String getPoliciesQuery2 = "SELECT * FROM Policy WHERE POLICY.customer_id = " + custId;
                        rs = stmt.executeQuery(getPoliciesQuery2);
                        System.out.println("List of your policies:\n");
                        while(rs.next()){
                        System.out.print("Policy ID: " +rs.getInt("policy_id"));
                        System.out.print(", Policy Date: " + rs.getString("policy_date"));
                        System.out.print(", Policy Price: " + rs.getInt("policy_price"));
                        System.out.println(", Agent ID: " + rs.getInt("agent_id"));
                        policies[counter] = rs.getInt("policy_id");
                        counter ++;
                }
                if(counter == 0){
                    System.out.println("You have no policies. You must create a policy before making a claim.\n");
                    break;
                }
                System.out.println("");
                
                boolean isnotPolicy = true;
                while(isnotPolicy){
                System.out.print("Enter the policy ID of a policy you own: ");
                
                policyIdCheck = intCheck();
                for(int i =0 ; i< policies.length ; i++){
                    if (policies[i] == policyIdCheck){
                        isnotPolicy = false;
                    }
                }
                if(isnotPolicy){
                System.out.println("You do not own this policy. Invalid policy ID. Try again");
                }
            }

                int claimCost = (int)(Math.random() * 10000) + 950;
                        
                        claimInsert = "INSERT INTO CLAIM VALUES(" +claimIDBASE + ", '" + claim_Type + "' ,'" + claimDesc + "' , " + policyIdCheck + ", " + custId + ", " + randomAdjustorID() + ", " + randomCompanyID() + ", " + claimCost + ", 'No')" ;
                        stmt.executeUpdate(claimInsert);
                        System.out.println("\n Your claim has been added \n");
                        break;
                        case 3:

                        String customerClaims2 = "select * from claim where claim.customer_id = " + custId;
                        int[] claimList = new int[1000];
                        rs = stmt.executeQuery(customerClaims2);
                        int claimCount = 0;
                       while(rs.next()){
                       System.out.print("Claim ID: " + rs.getInt("claim_id"));
                       System.out.print(", Claim Description: " + rs.getString("claim_desc"));
                       System.out.print(", Policy ID: " + rs.getInt("policy_id"));
                       System.out.print(", Claim Type: " + rs.getString("claim_type"));
                       System.out.println(", Claim Cost: " + rs.getInt("claim_cost"));
                       System.out.println("");
                       claimList[claimCount] = rs.getInt("claim_id");
                       claimCount++;
                       }
                       boolean isnotClaim = true;
                       int claimIDremove = 0;
                    while(isnotClaim){
                       System.out.print("Enter the claim ID of the claim you want to remove: ");
                        claimIDremove= intCheck();
                    
                       for(int i =0 ; i< claimList.length ; i++){
                        if (claimList[i] == claimIDremove){
                            isnotClaim = false;
                        }
                    }
                    if(isnotClaim){
                    System.out.println("You do not own this claim. Invalid claim ID. Try again\n");
                    }
                }

            String deleteClaim = "DELETE FROM CLAIM WHERE CLAIM.claim_id = " + claimIDremove;
            stmt.executeUpdate(deleteClaim);
            System.out.println("Claim ID: "+ claimIDremove + " has been removed\n");
                        break;
                        case 4:
                        claimFlag = false;
                        break;
                    }

                }
                break;
                case 6:
                custMenuFlag = false;
                break;
            }
        }

            custFlag = false;
            System.out.println("Commiting data here....");
            DBConnection1.commit();
        } catch(SQLException sql){
            System.out.println(sql);
        }

    
}

    }
//END OF customerInterface()

public static int agentIdGenerator(){
int agentID = 0;
agentID = (int)(Math.random() *11);
return agentID;
}


public static void customerSubMenu(){
    System.out.println("1. View your profile information");
    System.out.println("2. View/Add/Remove a policy");
    System.out.println("3. View/Add/Remove payment method");
    System.out.println("4. View/Add/Remove a claim");
    System.out.println("5. Remove your profile");
    System.out.println("6. Exit to database main menu");
}

public static void custPolicyMenu(){
    System.out.println("\n1. View all policies");
    System.out.println("2. View auto policies");
    System.out.println("3. View home policies");
    System.out.println("4. View life policies");
    System.out.println("5. Insert auto policy");
    System.out.println("6. Insert home policy");
    System.out.println("7. Insert life policy");
    System.out.println("8. Remove a policy");
    System.out.println("9. Exit to cutomer interface");
}

public static void testConnection(){
    Console myConsole = System.console();
    int maxCust = 10012;
    int maxPolicy = 10000009;
    while(flag){
        System.out.print("Enter your Oracle User id: ");
        userId = input.nextLine();
        //System.out.print("Enter your Oracle password: ");
        char[] masker = myConsole.readPassword("Enter your Oracle password: ");
        pass = new String(masker);
        Arrays.fill(masker,' ');
        //System.out.println(pass);
        //userPassword = input.nextLine();

        try(Connection DBConnection1 = DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241",userId,pass);
        Statement stmt = DBConnection1.createStatement();)
        {
            DBConnection1.setAutoCommit(false);
            System.out.println("Connected to database");
            String customerQuery = "SELECT * FROM CUSTOMER";

            ResultSet rs = stmt.executeQuery(customerQuery);
            while(rs.next()){
                
                if(rs.getInt("customer_id")>maxCust){
                    maxCust = rs.getInt("customer_id");
                }
                //System.out.println(maxCust);
            }

            String policyQuery = "SELECT * FROM Policy";

            rs = stmt.executeQuery(policyQuery);
            while(rs.next()){
                
                if(rs.getInt("policy_id")>maxPolicy){
                    maxPolicy = rs.getInt("policy_id");
                }
                //System.out.println(maxCust);
            }
            int claimMax = 1000000010;
            String claimMaxer = "select claim_id from claim";

            rs = stmt.executeQuery(claimMaxer);

            while(rs.next()){
                if(rs.getInt("claim_id")>claimMax){
                    claimMax = rs.getInt("claim_id");
                }
            }
            claimIDBASE = claimMax +5;
            //System.out.println(claimIDBASE);


            flag = false;
        } catch (SQLException sqle){
            System.out.println("You entered an invalid username/password");
            int errorInfo = sqle.getErrorCode();
            if(errorInfo == 1017){
                System.out.print("Would you like to re-enter your password? (yes or no)");
                String reTryPassword = input.nextLine();
                if(reTryPassword.equals("yes")){
                    flag = true;
                }else{
                    flag = false;
                }
            }
        }
    
    
    }
    custIDBASE = maxCust+5;
    policyIDBASE = maxPolicy +5;
}
//End of testConnection()

public static int randomAdjustorID(){
    int adjustorId = 0;
    adjustorId = (int)(Math.random() * 11) + 100000;
    return adjustorId;
}

public static int randomCompanyID(){
    int companyId = 0;
    companyId = (int)(Math.random() * 10) + 1000000;
    return companyId;
}

public static int randomItemID(){
    int ItemId = 0;
    ItemId = (int)(Math.random() * 8) + 100000000;
    return ItemId;
}

public static void corporateInterface(){
    try(Connection DBConnection1 = DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241",userId,pass);
    Statement stmt = DBConnection1.createStatement();)
    {
        DBConnection1.setAutoCommit(false);
boolean corporateFlag = true;
while(corporateFlag){
corporateMenu();
int corporateSelection = oneTovar(7);
int totalRevenue = 0;
int autoSum = 0;
int homeSum = 0;
int lifeSum = 0;

switch(corporateSelection){
    case 1:
    String policySumQuery = "select policy_type.policy_type, policy.policy_date,policy.isPaid from policy, policy_type Where policy.policy_id = policy_type.policy_id";

    ResultSet rs = stmt.executeQuery(policySumQuery);
    
    int date = 0;
    while(rs.next()){
        String policyType = rs.getString("policy_type");
        date = rs.getInt("policy_date");
        if(rs.getString("isPaid").equals("Yes")){
        if(policyType.equals("Auto")){
            date = 2021 - date;
            autoSum += date * 5500;
            date = 0;
        }

        if(policyType.equals("Home")){
            date = 2021 - date;
            homeSum += date * 8750;
            date = 0;
        }

        if(policyType.equals("Life")){
            date = 2021 - date;
            lifeSum += date * 9750;
            date = 0;
        }
    }
}

    int autoProfit = autoSum;
    int homeProfit = homeSum;
    int lifeProfit = lifeSum;
    String profitQuery = "select claim_type,claim_cost,isPaid from claim";

    rs = stmt.executeQuery(profitQuery);
    String claimType = "";
    String isPaid = "";
    int claimCost = 0;
    while(rs.next()){
        claimType = rs.getString("claim_type");
        isPaid = rs.getString("isPaid");
        claimCost = rs.getInt("claim_cost");
        //System.out.println(claimType);
        //System.out.println(claimCost);

        if(isPaid.equals("Yes")){
        if(claimType.equals("Auto")){
            autoProfit -= claimCost;
        }

        if(claimType.equals("Home")){
            homeProfit -= claimCost;
        }

        if(claimType.equals("Life")){
            lifeProfit -= claimCost;
        }
    }
    }

    System.out.println("");
    System.out.println("--------------------------------------------------");
    System.out.println("Total accrued revenue since start date of premium");
    System.out.printf("%-20s%-15d\n", "Auto Premiums = ", autoSum);
    System.out.printf("%-20s%-15d\n", "Home Premiums = ", homeSum);
    System.out.printf("%-20s%-15d\n", "Life Premiums = ", lifeSum);

    System.out.println("");
    System.out.println("Total profit per policy type");
    System.out.printf("%-20s%-15d\n", "Auto Profit = ", autoProfit);
    System.out.printf("%-20s%-15d\n", "Home Profit = ", homeProfit);
    System.out.printf("%-20s%-15d\n", "Life Profit = ", lifeProfit);
    System.out.println("--------------------------------------------------");
    autoSum = 0;
    autoSum = 0;
    lifeSum = 0;
    break;
    case 2:
    String customerRevenue = "Select customer.customer_id , customer.customer_firstName, customer.customer_lastname, policy.policy_date,policy.policy_price from customer, policy Where customer.customer_id = policy.customer_id";

     rs = stmt.executeQuery(customerRevenue);
     int custRevenue = 0;
     String custFirstName = "";
     String custLastName = "";
     int policyDate1 = 0;
     int policyPrice1 = 0;

    System.out.println("");
    System.out.println("--------------------------------------------------");
    System.out.println("Revenue generated from each customer's policy");
     while(rs.next()){
         policyDate1 = rs.getInt("policy_date");
         policyPrice1 = rs.getInt("policy_price");
         custFirstName = rs.getString("customer_firstName");
         custLastName = rs.getString("customer_lastName");
         custRevenue = (2021 - policyDate1) * policyPrice1;
        System.out.printf("%-20s%-20s  $ %-15d\n", custFirstName, custLastName, custRevenue);
     }

     System.out.println("--------------------------------------------------");

    break;
    case 3:

    String claimQuery = "select * from claim";

    rs = stmt.executeQuery(claimQuery);
    while(rs.next()){
        if (rs.getString("isPaid").equals("Yes")){
            System.out.print("Claim ID: " + rs.getInt("claim_id"));
            System.out.print(", Claim Description: " + rs.getString("claim_desc"));
            System.out.print(", Policy ID: " + rs.getInt("policy_id"));
            System.out.print(", Customer ID: " + rs.getInt("customer_id"));
            System.out.print(", Adjustor ID: " + rs.getInt("adjustor_id"));
            System.out.print(", Company ID: " + rs.getInt("company_id"));
            System.out.print(", Claim Type: " + rs.getString("claim_type"));
            System.out.print(", Claim Cost: " + rs.getInt("claim_cost"));
            System.out.println(", Paid : " + rs.getString("isPaid"));
            System.out.println("");
        }

    }
    break;
    case 4:

    String policySumQuery1 = "select policy_type.policy_type, policy.policy_date, policy.isPaid from policy, policy_type Where policy.policy_id = policy_type.policy_id";

     rs = stmt.executeQuery(policySumQuery1);
    
    int date1 = 0;
    while(rs.next()){
        String policyType = rs.getString("policy_type");
        date1 = rs.getInt("policy_date");
        if(rs.getString("isPaid").equals("Yes")){
        if(policyType.equals("Auto")){
            date1 = 2021 - date1;
            autoSum += date1 * 5500;
            date1 = 0;
        }

        if(policyType.equals("Home")){
            date1 = 2021 - date1;
            homeSum += date1 * 8750;
            date1 = 0;
        }

        if(policyType.equals("Life")){
            date1 = 2021 - date1;
            lifeSum += date1 * 9750;
            date1 = 0;
        }
    }
    }
    totalRevenue = autoSum + homeSum + lifeSum;
    System.out.println("");
    System.out.println("--------------------------------------------------");
    System.out.println("Total accrued revenue");
    System.out.printf("%-20s%-15d\n", "Auto Premiums = ", autoSum);
    System.out.printf("%-20s%-15d\n", "Home Premiums = ", homeSum);
    System.out.printf("%-20s%-15d\n", "Life Premiums = ", lifeSum);
    System.out.println("");
    System.out.printf("%-20s%-15d\n", "Total Revenue = ", totalRevenue);
    System.out.println("--------------------------------------------------");
    System.out.println("");
    break;
    case 5:
    String claimQuery2 = "select * from claim";

    rs = stmt.executeQuery(claimQuery2);
    System.out.println("List of all claims:\n");
    while(rs.next()){
        System.out.print("Claim ID: " + rs.getInt("claim_id"));
        System.out.print(", Claim Description: " + rs.getString("claim_desc"));
        System.out.print(", Policy ID: " + rs.getInt("policy_id"));
        System.out.print(", Customer ID: " + rs.getInt("customer_id"));
        System.out.print(", Adjustor ID: " + rs.getInt("adjustor_id"));
        System.out.print(", Company ID: " + rs.getInt("company_id"));
        System.out.print(", Claim Type: " + rs.getString("claim_type"));
        System.out.print(", Claim Cost: " + rs.getInt("claim_cost"));
        System.out.println(", Paid : " + rs.getString("isPaid"));
        System.out.println("");
    }
    break;
    case 6:
    String getPoliciesQuery2 = "SELECT * FROM Policy";
    rs = stmt.executeQuery(getPoliciesQuery2);
    System.out.println("List of all policies:\n");
        while(rs.next()){
            System.out.print("Policy ID: " +rs.getInt("policy_id"));
            System.out.print(", Policy Date: " + rs.getString("policy_date"));
            System.out.print(", Policy Price: " + rs.getInt("policy_price"));
            System.out.print(", Customer ID: " + rs.getInt("policy_price"));
            System.out.print(", Agent ID: " + rs.getInt("agent_id"));
            System.out.println(", Paid: " + rs.getString("isPaid"));
            System.out.println("");
            }

    break;
    case 7:
    corporateFlag = false;
    break;
    }
}
System.out.println("Commiting data here....");
DBConnection1.commit();
    } catch (SQLException sqle){
System.out.println(sqle);
    }



}

public static void corporateMenu(){
    System.out.println("1. Profits based on Policy Type");
    System.out.println("2. Revenue based on Customer");
    System.out.println("3. Claims paid");
    System.out.println("4. Total revenue");
    System.out.println("5. View all claims");
    System.out.println("6. View all policies");
    System.out.println("7. Return to Main Menu");
    System.out.println("");
}

public static void agentMenu(){
    System.out.println("1. View customers with unpaid policies");
    System.out.println("2. View customers with unpaid claims");
    System.out.println("3. Revenue generated per agent");
    System.out.println("4. Return to Main Menu");
    System.out.println("");
}



public static void agentInterface(){
    try(Connection DBConnection1 = DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241",userId,pass);
    Statement stmt = DBConnection1.createStatement();)
    {
        DBConnection1.setAutoCommit(false);
        boolean agentMenuFlag = true;
        while(agentMenuFlag){
            agentMenu();
            int agentSelection = oneTovar(4);
            ResultSet rs;
            switch(agentSelection){
            case 1:
            String unpaidPolicy = "select customer.customer_firstName, customer.customer_lastName, policy.policy_id, policy.isPaid from customer, policy WHERE customer.customer_id = policy.customer_id";

            rs = stmt.executeQuery(unpaidPolicy);

            String isPaid1 = "";
            while(rs.next()){
            isPaid1 = rs.getString("isPaid");
            if(isPaid1.equals("No")){
            System.out.print("First name: " + rs.getString("customer_firstName"));
            System.out.print(", Last name: " + rs.getString("customer_lastName"));
            System.out.print(", Policy ID: " + rs.getInt("policy_id"));
            System.out.println(", Paid : " + rs.getString("isPaid"));
            System.out.println("");
            }
            }
            
            break;
            case 2:

            String unpaidClaim = "select customer.customer_firstName, customer.customer_lastName, claim.claim_id, claim.isPaid from customer, claim WHERE customer.customer_id = claim.customer_id";

            rs = stmt.executeQuery(unpaidClaim);

            String isPaid2 = "";
            while(rs.next()){
            isPaid2 = rs.getString("isPaid");
            if(isPaid2.equals("No")){
            System.out.print("First name: " + rs.getString("customer_firstName"));
            System.out.print(", Last name: " + rs.getString("customer_lastName"));
            System.out.print(", Claim ID: " + rs.getInt("claim_id"));
            System.out.println(", Paid : " + rs.getString("isPaid"));
            System.out.println("");
            }
            }

            break;
            case 3:

            String agentRevenue = "select agent.*, policy.policy_id, policy.policy_date, policy.policy_price from agent, policy Where agent.customer_id = policy.customer_id";
            int agentId1 = 0;
            int date = 0;
            int policyPricer = 0;
            rs = stmt.executeQuery(agentRevenue);

            int agent0 = 0;
            int agent0Policy = 0;
            String agent0first = "";
            String agent0Last = "";

            int agent1 = 0;
            int agent1Policy = 0;
            String agent1first = "";
            String agent1Last = "";

            int agent2 = 0;
            int agent2Policy = 0;
            String agent2first = "";
            String agent2Last = "";


            int agent3 = 0;
            int agent3Policy = 0;
            String agent3first = "";
            String agent3Last = "";

            int agent4 = 0;
            int agent4Policy = 0;
            String agent4first = "";
            String agent4Last = "";


            int agent5 = 0;
            int agent5Policy = 0;
            String agent5first = "";
            String agent5Last = "";


            int agent6 = 0;
            int agent6Policy = 0;
            String agent6first = "";
            String agent6Last = "";


            int agent7 = 0;
            int agent7Policy = 0;
            String agent7first = "";
            String agent7Last = "";


            int agent8 = 0;
            int agent8Policy = 0;
            String agent8first = "";
            String agent8Last = "";


            int agent9 = 0;
            int agent9Policy = 0;
            String agent9first = "";
            String agent9Last = "";


            int agent10 = 0;
            int agent10Policy = 0;
            String agent10first = "";
            String agent10Last = "";


            while(rs.next()){
                agentId1 = rs.getInt("agent_id");
                date = rs.getInt("policy_date");
                policyPricer = rs.getInt("policy_price");
                if(agentId1 == 0){
                    agent0Policy++;
                    date = 2021 -date;
                    agent0 += (date * policyPricer);
                    agent0first = rs.getString("agent_firstName");
                    agent0Last = rs.getString("agent_lastName");
                }

                if(agentId1 == 1){
                    agent1Policy++;
                    date = 2021 -date;
                    agent1 += (date * policyPricer);
                    agent1first = rs.getString("agent_firstName");
                    agent1Last = rs.getString("agent_lastName");
                }

                if(agentId1 == 2){
                    agent2Policy++;
                    date = 2021 -date;
                    agent2 += (date * policyPricer);
                    agent2first = rs.getString("agent_firstName");
                    agent2Last = rs.getString("agent_lastName");
                }

                if(agentId1 == 3){
                    agent3Policy++;
                    date = 2021 -date;
                    agent3 += (date * policyPricer);
                    agent3first = rs.getString("agent_firstName");
                    agent3Last = rs.getString("agent_lastName");
                }

                if(agentId1 == 4){
                    agent4Policy++;
                    date = 2021 -date;
                    agent4 += (date * policyPricer);
                    agent4first = rs.getString("agent_firstName");
                    agent4Last = rs.getString("agent_lastName");
                }

                if(agentId1 == 5){
                    agent5Policy++;
                    date = 2021 -date;
                    agent5 += (date * policyPricer);
                    agent5first = rs.getString("agent_firstName");
                    agent5Last = rs.getString("agent_lastName");
                }

                if(agentId1 == 6){
                    agent6Policy++;
                    date = 2021 -date;
                    agent6 += (date * policyPricer);
                    agent6first = rs.getString("agent_firstName");
                    agent6Last = rs.getString("agent_lastName");
                }

                if(agentId1 == 7){
                    agent7Policy++;
                    date = 2021 -date;
                    agent7 += (date * policyPricer);
                    agent7first = rs.getString("agent_firstName");
                    agent7Last = rs.getString("agent_lastName");
                }

                if(agentId1 == 8){
                    agent8Policy++;
                    date = 2021 -date;
                    agent8 += (date * policyPricer);
                    agent8first = rs.getString("agent_firstName");
                    agent8Last = rs.getString("agent_lastName");
                }

                if(agentId1 == 9){
                    agent9Policy++;
                    date = 2021 -date;
                    agent9 += (date * policyPricer);
                    agent9first = rs.getString("agent_firstName");
                    agent9Last = rs.getString("agent_lastName");
                }

                if(agentId1 == 10){
                    agent10Policy++;
                    date = 2021 -date;
                    agent10 += (date * policyPricer);
                    agent10first = rs.getString("agent_firstName");
                    agent10Last = rs.getString("agent_lastName");
                }
               


            }

            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("Total Revenue per agent: \n");
            System.out.printf("%-15s%-15s%-18s%-10d\n", agent0first, agent0Last, "Total Revenue = ", agent0);
            System.out.printf("%-15s%-15s%-18s%-10d\n", agent1first, agent1Last, "Total Revenue = ", agent1);
            System.out.printf("%-15s%-15s%-18s%-10d\n", agent2first, agent2Last, "Total Revenue = ", agent2);
            System.out.printf("%-15s%-15s%-18s%-10d\n", agent3first, agent3Last, "Total Revenue = ", agent3);
            System.out.printf("%-15s%-15s%-18s%-10d\n", agent4first, agent4Last, "Total Revenue = ", agent4);
            System.out.printf("%-15s%-15s%-18s%-10d\n", agent5first, agent5Last, "Total Revenue = ", agent5);
            System.out.printf("%-15s%-15s%-18s%-10d\n", agent6first, agent6Last, "Total Revenue = ", agent6);
            System.out.printf("%-15s%-15s%-18s%-10d\n", agent7first, agent7Last, "Total Revenue = ", agent7);
            System.out.printf("%-15s%-15s%-18s%-10d\n", agent8first, agent8Last, "Total Revenue = ", agent8);
            System.out.printf("%-15s%-15s%-18s%-10d\n", agent9first, agent9Last, "Total Revenue = ", agent9);
            System.out.printf("%-15s%-15s%-18s%-10d\n", agent10first, agent10Last, "Total Revenue = ", agent10);
            System.out.println("\n--------------------------------------------------------------------------------");

            break;
            case 4:
            agentMenuFlag = false;
            
            break;
            }
        }
        DBConnection1.commit();
        System.out.println("Commiting changes here .....");

    } catch(SQLException sql){
        System.out.println(sql);
    }
}




//THIS IS THE END OF THE CLASS
}
//THIS IS THE END OF THE CLASS





    
    

