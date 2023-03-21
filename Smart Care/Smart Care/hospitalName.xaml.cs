using System;
using System.Collections.Generic;
using System.Linq;
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
    /// Interaction logic for hospitalName.xaml
    /// </summary>
    public partial class hospitalName : Window
    {
        string selectedHospital;
        string admin_id;

        public hospitalName()
        {
            InitializeComponent();

            admin_id = Login.instance.currentUserAdminID;

            getWelcomeMessage();
            getHospitalName();
        }
        private void getWelcomeMessage()
        {
            MessageBox.Show("Hello Admin, welcome to Smart Care Admin System. Since this is your first time, we will be asking you to enter your Hospital Name, please click 'Ok' to continue. Thankyou!");
        }

        private async void proceedBtn_Click(object sender, RoutedEventArgs e)
        {
            if(hospitalName1.Text == "")
            {
                MessageBox.Show("Hospital name is required!");
                hospitalName1.Focus();
            }

            else
            {

                var url = "http://192.168.43.66:8080/smartcare/updateHospitalname.php";

                var client = new HttpClient();

                var data = new Dictionary<string, string>
                {
                    {"hospital_name", selectedHospital},
                    {"admin_id", admin_id }
                };


                var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
                var content = await httpResponseMessage.Content.ReadAsStringAsync();

                if(content == "success")
                {
                    MessageBox.Show("Thankyou, you may now proceed!");

                    //string body = content.Substring(0, body.IndexOf("`"));
                    //MessageBox.Show(body);
                    Home home = new Home();
                    //this will open your child window
                    home.Show();
                    //this will close parent window. windowOne in this case
                    this.Close();

                }

                else
                {
                    MessageBox.Show("Failure to update hospital name.", "Update Error", MessageBoxButton.OK, MessageBoxImage.Error);
                }

            }
        }

        private void hospitalName1_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            selectedHospital = hospitalName1.SelectedItem.ToString();
        }

        private void getHospitalName()
        {

            int e = 0;

            HttpClient client2 = new HttpClient();
            client2.BaseAddress = new Uri("http://192.168.43.66:8080/smartcare/");
            client2.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));


            HttpResponseMessage response2 = client2.GetAsync("getHospitalNames.php").Result;

            if (response2.IsSuccessStatusCode)
            {
                var hospitalNames = response2.Content.ReadAsStringAsync().Result;

                for (; ; )
                {
                    if (hospitalNames == "" || hospitalNames == " ")
                    {
                        break;
                    }

                    else
                    {
                        string hospital_name = hospitalNames.Substring(0, hospitalNames.IndexOf("`"));
                        int hospital_name_length = hospital_name.Length;
                        int hospital_name_length1 = hospital_name_length + 1;
                        string finalNewList = hospitalNames.Remove(0, hospital_name_length1);

                        hospitalNames = finalNewList;

                        hospitalName1.Items.Insert(e, hospital_name);

                        e++;
                    }
                }
            }

            else
            {
                MessageBox.Show("Connection Failure.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }

            hospitalName1.SelectedIndex = 0;
        }
    }
}
