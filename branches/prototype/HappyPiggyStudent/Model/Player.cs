using System;
using System.Collections.Generic;

namespace HappyPiggyStudent.Model
{
    [Serializable]
    public class Player : PlayerBase
    {
        public double cash;
        public List<Loan> loan_list;
        public List<Deposit> deposit_list;

        public Player()
            : base()
        {
            loan_list = new List<Loan>();
            deposit_list = new List<Deposit>();
        }

        public Player(int id, StudentDescription student_info, double cash)
            : base(id, student_info)
        {
            this.cash = cash;
            loan_list = new List<Loan>();
            deposit_list = new List<Deposit>();
        }

        public Player(Player other, bool deep)
        {
            cash = other.cash;
            student_info = other.student_info;
            id = other.id;
            if (deep)
            {
                share_list = new List<Share>(other.share_list);
                loan_list = new List<Loan>(other.loan_list);
                deposit_list = new List<Deposit>(other.deposit_list);
            }
            else
            {
                share_list = new List<Share>();
                loan_list = new List<Loan>();
                deposit_list = new List<Deposit>();
            }
        }
    }
}
