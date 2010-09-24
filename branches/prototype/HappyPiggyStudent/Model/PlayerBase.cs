
using System;
using System.Collections.Generic;
using System.Xml.Serialization;
using System.Xml.Schema;

namespace HappyPiggyStudent.Model
{
    [Serializable]
    public class PlayerBase
    {
        public int id;
        public StudentDescription student_info;
        [XmlElementAttribute("share", Form = XmlSchemaForm.Unqualified, Type = typeof(Share))]
        public List<Share> share_list;

        public PlayerBase()
        {
            share_list = new List<Share>();
        }

        public PlayerBase(int id, StudentDescription student_info)
        {
            this.id = id;
            this.student_info = student_info;
            share_list = new List<Share>();
        }

        public PlayerBase(PlayerBase other, bool deep)
        {
            id = other.id;
            student_info = other.student_info;
            if (deep)
                share_list = new List<Share>(other.share_list);
            else
                share_list = new List<Share>();
        }
    }
}
