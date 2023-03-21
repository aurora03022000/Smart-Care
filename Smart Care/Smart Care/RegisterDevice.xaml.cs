using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Text.RegularExpressions;
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
    /// Interaction logic for RegisterDevice.xaml
    /// </summary>
    public partial class RegisterDevice : Window
    {
        public static RegisterDevice instance;

        public RegisterDevice()
        {
            InitializeComponent();
        }

        private async void Reg_Button_Click(object sender, RoutedEventArgs e)
        {

            if (deviceID.Text == "")
            {
                MessageBox.Show("Device ID is Required");

                deviceID.Focus();
            }


            else if (ipAddress.Text == "")
            {
                MessageBox.Show("IP Address is Required");

                ipAddress.Focus();
            }

            else
            {
                //mag kwa value
                string device_Id1 = deviceID.Text;
                string ipaddress1 = ipAddress.Text;//
                var url = "http://192.168.43.66:8080/smartcare/registerDevice.php";

                var client = new HttpClient();

                var data = new Dictionary<string, string>
                            {
                                {"device_id", device_Id1},
                                {"ipaddress", ipaddress1}
                            };

                var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
                var content = await httpResponseMessage.Content.ReadAsStringAsync();

                if (content.ToString() == "success")
                {
                    MessageBox.Show("Device registered successfully");
                    deviceID.Text = "";
                    ipAddress.Text = "";
                    deviceID.Focus();
                    Home.instance.deviceDataGrid.Items.Clear();
                    Home.instance.getAllDeviceID();

                    this.Close();
                }

                else if (content.ToString() == "device id taken")
                {
                    MessageBox.Show("Device ID is already taken.", "Device Error", MessageBoxButton.OK, MessageBoxImage.Error);
                    deviceID.Text = "";
                    deviceID.Focus();

                }

                else if (content.ToString() == "device ip taken")
                {
                    MessageBox.Show("Device IP Address is already taken.", "Device Error", MessageBoxButton.OK, MessageBoxImage.Error);
                    ipAddress.Text = "";
                    ipAddress.Focus();
                }

                else
                {
                    MessageBox.Show("Failed to register device");
                }
            }




        }


        private void TypeNumericValidation(object sender, TextCompositionEventArgs e)
        {
            e.Handled = new Regex("[^0-9]+").IsMatch(e.Text);
        }

    }
}
