using System;


namespace HappyPiggyStudent.Model
{
    [Serializable]
    public struct Loan : ICloneable
    {
        public Bank bank;
        public Player player_recieved;
        public double issued_money;
        public int issued_date;
        public double return_money;
        public int return_date;

        public Loan(Bank bank, Player player_recieved, double issued_money, int issued_date,
            double return_money, int return_date)
        {
            this.player_recieved = player_recieved;
            this.bank = bank;
            this.issued_money = issued_money;
            this.issued_date = issued_date;
            this.return_money = return_money;
            this.return_date = return_date;
        }
        public Loan(Loan other)
        {
            bank = other.bank;
            player_recieved = other.player_recieved;
            issued_money = other.issued_money;
            issued_date = other.issued_date;
            return_money = other.return_money;
            return_date = other.return_date;
        }

        public object Clone()
        {
            return new Loan(this);
        }
    }
}
