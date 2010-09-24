using System;
using System.Collections.Generic;

namespace HappyPiggyStudent.Model
{
    [Serializable]
    public class DivVoting : ICloneable
    {
        public struct VotePair
        {
            int v_num;
            double dpr;
        }
        public Company company;
        public List<VotePair> votes;
        public List<double> possible_dpr; 

        public DivVoting()
        {
            votes = new List<VotePair>();
            possible_dpr = new List<double>();
        }

        public DivVoting(Company company)
        {
            this.company = company;
            votes = new List<VotePair>();
            possible_dpr = new List<double>();
        }

        public DivVoting(DivVoting other, bool deep)
        {
            company = other.company;
            if (deep)
            {
                votes = new List<VotePair>(other.votes);
                possible_dpr = new List<double>(other.possible_dpr);
            }
            else
            {
                votes = new List<VotePair>();
                possible_dpr = new List<double>();
            }
        }

        public object Clone()
        {
            return new DivVoting(this, true);
        }
    }
}
