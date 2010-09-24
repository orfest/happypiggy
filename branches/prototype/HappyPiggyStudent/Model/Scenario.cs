using System;
using System.Collections.Generic;
using System.Xml.Serialization;
using System.Xml.Schema;

namespace HappyPiggyStudent.Model
{
    [Serializable]
    [XmlRoot("scenario")]
    public class Scenario
    {
        [XmlElementAttribute("comp_list", Form = XmlSchemaForm.Unqualified, Type = typeof(Company))]
        public List<Company> comp_list;
        
        public Scenario()
        {}
        
    }
}
