using System;
using System.Collections.Generic;
using System.Xml.Serialization;

namespace HappyPiggyStudent.Model
{
    [Serializable]
    public class CompanyMessage : ICloneable
    {
        public enum MessageType
        {
            Rumor,
            OfficialInfo
        }
        public int id;
        public MessageType type;
        [XmlIgnore]
        public Company company;
        public string text;
        public string title;
        [XmlIgnore]
        public List<PlayerBase> recievers;
        public double k_mod;
        public double c_mod;
        public double getNewEstimatedProfit(double current)
        {
            return current * k_mod + c_mod;
        }

        public CompanyMessage()
        {
            recievers = new List<PlayerBase>();
        }

        public CompanyMessage(int id, MessageType type, Company company, string text, double k_mod, double c_mod)
        {
            this.id = id;
            this.type = type;
            this.company = company;
            this.text = text;
            recievers = new List<PlayerBase>();
            this.k_mod = k_mod;
            this.c_mod = c_mod;
        }
        public CompanyMessage(CompanyMessage other, bool deep)
        {
            id = other.id;
            type = other.type;
            company = other.company;
            text = other.text;
            if (deep)
                recievers = new List<PlayerBase>(other.recievers);
            else
                recievers = new List<PlayerBase>();
            k_mod = other.k_mod;
            c_mod = other.c_mod;
        }



        public object Clone()
        {
            return new CompanyMessage(this, true);
        }
    }
}
