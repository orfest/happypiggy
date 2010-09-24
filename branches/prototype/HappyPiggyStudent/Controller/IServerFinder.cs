using System.Collections.Generic;

namespace HappyPiggyStudent.Controller
{
    public struct HostPortVersion
    {
        public string host;
        public int port;
        public string version;
    }
    public interface IServerFinder
    {
        List<string> enumerateText();
        List<HostPortVersion> enumerateHostPort();
    }

    public class PrototypeServerFinder : IServerFinder
    {
        public List<string> enumerateText()
        {
            List<string> rez = new List<string>(1);
            rez.Add("Сервер прототипа [Версия: прототип]");
            return rez;
        }

        public List<HostPortVersion> enumerateHostPort()
        {
            List<HostPortVersion> rez = new List<HostPortVersion>(1);
            rez.Add(new HostPortVersion());
            return rez;
        }
    }
}
