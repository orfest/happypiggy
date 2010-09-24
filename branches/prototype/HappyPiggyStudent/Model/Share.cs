
using System;

namespace HappyPiggyStudent.Model
{
    [Serializable]
    public class Share
    {
        public Company company;
        public int num;
        public PlayerBase owner;

        public Share()
        { }

        public Share(Share other)
        {
            company = other.company;
            num = other.num;
            owner = other.owner;
        }
        public Share(Company company, int num, PlayerBase owner)
        {
            this.company = company;
            this.num = num;
            this.owner = owner;
        }
    }
}
