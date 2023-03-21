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
    /// Interaction logic for hospitalList.xaml
    /// </summary>
    public partial class hospitalList : Window
    {
        public struct hospitalData
        {
            public int hospital_id { set; get; }
            public string hospital_name { set; get; }
        }

        public hospitalList()
        {
            InitializeComponent();

            hospitalDataGrid.Columns[0].MaxWidth = 0;

            getHospitalNames();
        }

        private async void btnDeleteHospital_Click(object sender, RoutedEventArgs e)
        {
            var DialogResult = MessageBox.Show("Are you sure you want to delete this Hospital name?", "Delete Hospital Name", MessageBoxButton.YesNo, MessageBoxImage.Question, MessageBoxResult.No);

            if (DialogResult == MessageBoxResult.Yes)
            {
                var selectedIndex1 = hospitalDataGrid.SelectedIndex;
                TextBlock x1 = hospitalDataGrid.Columns[0].GetCellContent(hospitalDataGrid.Items[selectedIndex1]) as TextBlock;
                string staffID1 = x1.Text;

                var url = "http://192.168.43.66:8080/smartcare/removeHospitalName.php";

                var client = new HttpClient();

                var data = new Dictionary<string, string>{
                {"hospital_id", staffID1}
                };

                var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
                var content = await httpResponseMessage.Content.ReadAsStringAsync();

                if (content == "success")
                {
                    MessageBox.Show("Hospital name deleted successfully");
                    hospitalDataGrid.Items.Clear();
                    getHospitalNames();
                }

                else
                {
                    MessageBox.Show("Delete operation error", "Delete Error", MessageBoxButton.OK, MessageBoxImage.Error);
                }

            }
        }


        public void getHospitalNames()
        {
            HttpClient client2 = new HttpClient();
            client2.BaseAddress = new Uri("http://192.168.43.66:8080/smartcare/");
            client2.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));


            HttpResponseMessage response2 = client2.GetAsync("getHospitalNames1.php").Result;

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
                        string hospital_id = hospitalNames.Substring(0, hospitalNames.IndexOf("`"));
                        int hospital_id_int = Convert.ToInt32(hospital_id);// returns int
                        int hospital_id_length = hospital_id.Length;
                        int hospital_id_length1 = hospital_id_length + 1;
                        string finalNewList = hospitalNames.Remove(0, hospital_id_length1);

                        hospitalNames = finalNewList;

                        string hospital_name = hospitalNames.Substring(0, hospitalNames.IndexOf("`"));
                        int hospital_name_length = hospital_name.Length;
                        int hospital_name_length1 = hospital_name_length + 1;
                        string finalNewList1 = hospitalNames.Remove(0, hospital_name_length1);

                        hospitalNames = finalNewList1;

                      
                        hospitalDataGrid.Items.Add(new hospitalData { hospital_id = hospital_id_int, hospital_name = hospital_name });
                    }
                }
            }

            else
            {
                MessageBox.Show("Connection Failure.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private async void addHospital_Click(object sender, RoutedEventArgs e)
        {
            var url = "http://192.168.43.66:8080/smartcare/addHospitalName.php";

            var client = new HttpClient();

            var data = new Dictionary<string, string>{
                {"hospital_name", hospitalNameTextBox.Text}
            };

            var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
            var content = await httpResponseMessage.Content.ReadAsStringAsync();

            if (content == "success")
            {
                MessageBox.Show("Hospital name added successfully");
                hospitalDataGrid.Items.Clear();
                getHospitalNames();
            }

            else if (content == "duplicate")
            {
                MessageBox.Show("Hospital name already exist.", "Hospital Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }


            else
            {
                MessageBox.Show("Failure to add hospital name.", "Hospital Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }
    }
}
