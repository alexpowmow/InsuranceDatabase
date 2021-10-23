Included in this zipped folder is the executable for the insuraceDatabase.java program, a .class file, a txt file to connect the two and that ojdbc8.jar

NOTE: The command 'make' can be used to run this program
NOTE: CUSTOMER ID 10000 is a very good one to use for tests

This program has three main interfaces:

1. Corporate Management Interface
- Calculates profits based on policy type (Auto/Home/Life)
- Calculates revenue based on each policy and displays which customer owns each policy
- Displays the values of all claims that have not been paid
- Displays the total revenue from all policies
- Displays all claims for management purposes
- Displays all policies for management purposes

**Note** 
All revenues and profits are compounded over a yearly basis based on the purchase date of the policy.

2. Customer Interface
- Allows a customer to log onto an existing account or create a new account
    a. If they choose to create a new account they are assigned an agent and unique customer ID
    b. If they choose an existing account they log in by selecting their customer id from a list of customers. Once in the interface they have access to all their assets

- Once in the interface they can retrieve their personal profile information
- They are capable of viewing policies, creating new policies, and removing policies
- They are capable of viewing payment methods, creating new payment methods, and removing a current payment method. They can only have one payment method at a time
- They are capable of viewing claims, adding claims and removing claims ONLY if they have an active policy
- If they choose to remove their profile all of their policies will be deleted, all of their claims will be deleted, all of their payment methods will be deleted, and all of their payment methods will be deleted

3.
- The agent can view customers who have not paid their policies
- The agent can view customers who have not paid their claims
- The agent can view the revenue generated per agent


