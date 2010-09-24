using System;

namespace HappyPiggyStudent.Model
{
    [Serializable]
    public struct BuyOffer
    {
        public Share share;
        public Player player;
        public double value;

        public BuyOffer(Share share, Player player, double value)
        {
            this.share = share;
            this.player = player;
            this.value = value;
        }

        public BuyOffer(BuyOffer other)
        {
            share = other.share;
            player = other.player;
            value = other.value;
        }
    }
}
