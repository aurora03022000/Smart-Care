using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace Smart_Care
{
    /// <summary>
    /// Interaction logic for userAccount.xaml
    /// </summary>
    public partial class userAccount : Window
    {
        public userAccount()
        {
            InitializeComponent();

            HttpClient client = new HttpClient();
            client.BaseAddress = new Uri("http://192.168.43.66:8080/smartcare/");
            //client.DefaultRequestHeaders.Add("appkey", "myapp_key");
            client.DefaultRequestHeaders.Accept.Add(
               new MediaTypeWithQualityHeaderValue("application/json"));

            HttpResponseMessage response = client.GetAsync("getUserProfile.php").Result;
            if (response.IsSuccessStatusCode)
            {
                var userProfile = response.Content.ReadAsStringAsync().Result;
                string userProfileString = userProfile.ToString();
                setUserProfile(userProfileString);
            }
            else
            {
                MessageBox.Show("Error Code" + response.StatusCode + " : Message - " + response.ReasonPhrase);
            }
        }

        private void setUserProfile(string profileUrl)
        {
            userProfile.Source = new BitmapImage(new Uri("http://192.168.43.66:8080/smartcare/" + profileUrl));
        }
    }


    
}
