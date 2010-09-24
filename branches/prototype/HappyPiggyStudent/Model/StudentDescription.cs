using System;

namespace HappyPiggyStudent.Model
{
    [Serializable]
    public struct StudentDescription
    {
        public string name;
        public int group_number;
        public string passwd;

        public StudentDescription(string name, int group_number, string passwd)
        {
            this.name = name;
            this.passwd = passwd;
            this.group_number = group_number;
        }

        public StudentDescription(StudentDescription other)
        {
            name = other.name;
            passwd = other.passwd;
            group_number = other.group_number;
        }

    }
}
