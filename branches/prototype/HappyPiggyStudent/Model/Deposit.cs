using System;

namespace HappyPiggyStudent.Model
{
    [Serializable]
    public struct Deposit : ICloneable
    {
        public Bank bank;
        public Player player;
        public double money;
        public int date;
        public double return_money;
        public int return_date;


        public Deposit(Bank bank, Player player, double money, int date, double return_money, int return_date)
        {
            this.bank = bank;
            this.player = player;
            this.money = money;
            this.date = date;
            this.return_money = return_money;
            this.return_date = return_date;
        }

        public Deposit(Deposit other)
        {
            bank = other.bank;
            player = other.player;
            money = other.money;
            date = other.date;
            return_money = other.return_money;
            return_date = other.return_date;
        }



        public object Clone()
        {
            return new Deposit(this);
        }
    }
}
