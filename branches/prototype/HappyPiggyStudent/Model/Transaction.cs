using System;

namespace HappyPiggyStudent.Model
{
    [Serializable]
    public struct Transaction : ICloneable
    {
        public int turn_number;
        public int time;
        public Share share;
        public PlayerBase seller;
        public PlayerBase buyer;
        public double value;

        public Transaction(int turn_number, int time, Share share, PlayerBase seller, PlayerBase buyer, double value)
        {
            this.turn_number = turn_number;
            this.time = time;
            this.share = share;
            this.seller = seller;
            this.buyer = buyer;
            this.value = value;
        }

        public Transaction(Transaction other)
        {
            turn_number = other.turn_number;
            time = other.time;
            share = other.share;
            seller = other.seller;
            buyer = other.buyer;
            value = other.value;
        }

        public object Clone()
        {
            return new Transaction(this);
        }
    }
}
