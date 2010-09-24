using System.Collections.Generic;
using System;

namespace HappyPiggyStudent.Model
{
    [Serializable]
    public class Teacher : PlayerBase
    {
        public List<LoanToBank> loans_to_bank;

        public Teacher()
            : base()
        {
            loans_to_bank = new List<LoanToBank>();
        }

        public Teacher(PlayerBase pb, bool deep)
            : base(pb, deep)
        {
            loans_to_bank = new List<LoanToBank>();
        }

        public Teacher(int id, StudentDescription student_info)
            :base(id, student_info)
        {
            loans_to_bank = new List<LoanToBank>();
        }

        public Teacher(Teacher other, bool deep)
        {
            student_info = other.student_info;
            id = other.id;
            if (deep)
            {
                share_list = new List<Share>(other.share_list);
                loans_to_bank = new List<LoanToBank>(other.loans_to_bank);
            }
            else
            {
                share_list = new List<Share>();
                loans_to_bank = new List<LoanToBank>();
            }
        }
            
    }
}
