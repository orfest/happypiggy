using System;
using System.Collections.Generic;

namespace HappyPiggyStudent.Model
{
    [Serializable]
    public class Bank
    {
        public int id;
        public string name;
        public StudentDescription student;
        public double cash;
        public List<LoanToBank> loan_list;
        public List<Loan> issued_loans;
        public List<Deposit> deposits;
        public double loan_interest_rate;
        public double deposit_interest_rate;

        public Bank()
        {
            loan_list = new List<LoanToBank>();
            issued_loans = new List<Loan>();
            deposits = new List<Deposit>();
        }

        public Bank(int id, string name, StudentDescription student, double cash, double loan_interest_rate, double deposit_interest_rate)
        {
            this.id = id;
            this.name = name;
            this.student = student;
            this.cash = cash;
            loan_list = new List<LoanToBank>();
            issued_loans = new List<Loan>();
            deposits = new List<Deposit>();
            this.loan_interest_rate = loan_interest_rate;
            this.deposit_interest_rate = deposit_interest_rate;
        }

        public Bank(Bank other, bool deep)
        {
            id = other.id;
            name = other.name;
            student = other.student;
            cash = other.cash;
            if (deep)
            {
                loan_list = new List<LoanToBank>(other.loan_list);
                issued_loans = new List<Loan>(other.issued_loans);
                deposits = new List<Deposit>(other.deposits);
            }
            else
            {
                loan_list = new List<LoanToBank>();
                issued_loans = new List<Loan>();
                deposits = new List<Deposit>();
            }
            loan_interest_rate = other.loan_interest_rate;
            deposit_interest_rate = other.deposit_interest_rate;
        }

    }
}
