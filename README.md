# ATM-CLI Program Assessment

## Problem Statement
Implement an interface to an ATM in which the following use cases are accounted for:
- A customer can login to the ATM account by providing a 4-digit PIN number
- A customer can view their current balance
- A customer can deposit money
- A customer can withdraw money

## Assumptions
- Only one user can be logged into the ATM at a time.
- The ATM has an infinite amount of money to dispense.
- A user can only withdraw/deposit $1 to $1000 each transaction.

## My Initial Thoughts
- From the start of development, I had a general idea that I was going to create a CSV file to act as a stand-in database for this simulated ATM. This would allow the data to persist from one build to another.
- I implemented this program with input validation in mind. Through many validation checks, I tried to ensure that no input entered by a user could cause an unexpected result.
- I wanted this program to flow like a real ATM - so upon finishing any activity, I included a "Continue prompt", which simply asks the user if they would like to proceed with another action. If the user says yes, then they are prompted to re-enter their pin for an extra security measure.

## Structure
- ATM Class
  - Contains the main functionality of the entire program. 
- Account Class
  - Used to create Account objects, which contain all the data about the user account:
    - String acctName
    - int acctPin
    - int acctId
    - BigDecimal acctBalance
- AccountDatabase Class
  - Initially scans through the User_Database.csv file to retrieve all Accounts.
  - Fills an ArrayList of Account objects to be used to find the currentUser.
  - Contains the function that updates the CSV file upon depositing or withdrawing money.

# Program Guide
The program reads the User_Database.csv file in the root folder.
Format of all input CSV files should be `AcctName,AcctID,AcctPIN,AcctBalance`.

Program will output data from AccountDatabase class to User_Database.csv file throughout course of
program.

## Usage
All account ID and PIN combinations can be found in the User_Database.csv file.
Below is the first user:

| AcctName   | AcctId     | AcctPin    | AcctBalance |
|------------|------------|------------|-------------|
| Bill Dollar | 55555555   | 1234       | 100.00     |