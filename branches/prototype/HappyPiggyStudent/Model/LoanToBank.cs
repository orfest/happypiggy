using System;

namespace HappyPiggyStudent.Model
{
    [Serializable]
    public struct LoanToBank
    {
        public Bank bank;
        public double issued_money;
        public int issued_date;
        public double return_money;
        public int return_date;


        public LoanToBank(Bank bank, double issued_money, int issued_date, double return_money, int return_date)
        {
            this.bank = bank;
            this.issued_money = issued_money;
            this.issued_date = issued_date;
            this.return_money = return_money;
            this.return_date = return_date;
        }
        public LoanToBank(LoanToBank other)
        {
            bank = other.bank;
            issued_money = other.issued_money;
            issued_date = other.issued_date;
            return_money = other.return_money;
            return_date = other.return_date;
        }
    }
}
