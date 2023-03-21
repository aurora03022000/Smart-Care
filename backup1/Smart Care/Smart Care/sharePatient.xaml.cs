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
    /// Interaction logic for sharePatient.xaml
    /// </summary>
    public partial class sharePatient : Window
    {
        string selectedEncoder;
        string changeSelectedValue;

        public sharePatient()
        {
            InitializeComponent();

            getEncoder();

            checkShare();
        }


        private void getEncoder()
        {
            encoderComboBox.Items.Clear();
            shareEncoderComboBox.Items.Clear();

            int e = 0;

            HttpClient client2 = new HttpClient();
            client2.BaseAddress = new Uri("http://192.168.254.114:8080/smartcare/");
            client2.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));


            HttpResponseMessage response2 = client2.GetAsync("getStaffNamesAdmin.php").Result;

            if (response2.IsSuccessStatusCode)
            {
                var staffName = response2.Content.ReadAsStringAsync().Result;

                for (; ; )
                {
                    if (staffName == "" || staffName == " ")
                    {
                        break;
                    }

                    else
                    {
                        string staff_name = staffName.Substring(0, staffName.IndexOf("`"));
                        int staff_name_length = staff_name.Length;
                        int staff_name_length1 = staff_name_length + 1;
                        string finalNewList = staffName.Remove(0, staff_name_length1);

                        staffName = finalNewList;

                        encoderComboBox.Items.Insert(e, staff_name);
                        shareEncoderComboBox.Items.Insert(e, staff_name);

                        e++;
                    }
                }
            }

            else
            {
                MessageBox.Show("Connection Failure.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private async void sharePatientBtn_Click(object sender, RoutedEventArgs e)
        {
            var url = "http://192.168.254.114:8080/smartcare/sharePatientAdmin.php";

            var client = new HttpClient();

            var data = new Dictionary<string, string>{
                {"sharedPatientID1", selectedEncoder}
            };

            var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
            var content = await httpResponseMessage.Content.ReadAsStringAsync();

            if(content == "success")
            {
                MessageBox.Show("Patient Shared Successfully");
                notSharedGrid.Visibility = Visibility.Hidden;
                sharedGrid.Visibility = Visibility.Visible;
                shareUserName.Content = encoderComboBox.SelectedItem;
            }

            else
            {
                MessageBox.Show(content);
                MessageBox.Show("Share patient failed.", "Share Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void encoderComboBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (encoderComboBox.SelectedIndex != -1)
            {
                selectedEncoder = encoderComboBox.SelectedItem.ToString();
            }
        }


        private void checkShare()
        {
            HttpClient client2 = new HttpClient();
            client2.BaseAddress = new Uri("http://192.168.254.114:8080/smartcare/");
            client2.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));


            HttpResponseMessage response2 = client2.GetAsync("checkShare.php").Result;

            if (response2.IsSuccessStatusCode)
            {
                var shareID = response2.Content.ReadAsStringAsync().Result;
                int count = 0;
                for (; ; )
                {
                    if (shareID == "" || shareID == " ")
                    {
                        if(count == 0)
                        {
                            notSharedGrid.Visibility = Visibility.Visible;
                            sharedGrid.Visibility = Visibility.Hidden;
                        }
                        break;
                    }

                    else
                    {
                        count++;

                        string share_id = shareID.Substring(0, shareID.IndexOf("`"));
                        int share_id_length = share_id.Length;
                        int share_id_length1 = share_id_length + 1;
                        string finalNewList = shareID.Remove(0, share_id_length1);
                        
                        shareID = finalNewList;

                        if (share_id == "")
                        {
                            notSharedGrid.Visibility = Visibility.Visible;
                            sharedGrid.Visibility = Visibility.Hidden;
                        }

                        else
                        {
                            notSharedGrid.Visibility = Visibility.Hidden;
                            sharedGrid.Visibility = Visibility.Visible;
                        }

                        shareUserName.Content = shareID;

                        shareID = "";


                    }
                }

                
            }

            else
            {
                MessageBox.Show("Connection Failure.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void stopSharing_Click(object sender, RoutedEventArgs e)
        {
            var DialogResult = MessageBox.Show("Are you sure you want to stop sharing?", "Stop Sharing", MessageBoxButton.YesNo, MessageBoxImage.Question, MessageBoxResult.No);

            if (DialogResult == MessageBoxResult.Yes)
            {
                HttpClient client2 = new HttpClient();
                client2.BaseAddress = new Uri("http://192.168.254.114:8080/smartcare/");
                client2.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));


                HttpResponseMessage response2 = client2.GetAsync("stopSharingAdmin.php").Result;

                if (response2.IsSuccessStatusCode)
                {
                    MessageBox.Show("Successfully stopped patient sharing");
                    notSharedGrid.Visibility = Visibility.Visible;
                    sharedGrid.Visibility = Visibility.Hidden;
                    encoderComboBox.Items.Clear();
                    getEncoder();
                }

                else
                {
                    MessageBox.Show("Connection Failure.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                }
            }
            
        }

        private void change_Click(object sender, RoutedEventArgs e)
        {
            shareLabel.Content = "Share to:";

            save.Visibility = Visibility.Visible;
            cancel.Visibility = Visibility.Visible;
            change.Visibility = Visibility.Hidden;
            stopSharing.Visibility = Visibility.Hidden;

            shareEncoderComboBox.Visibility = Visibility.Visible;
            shareUserName.Visibility = Visibility.Hidden;
            shareEncoderComboBox.Items.Clear();
            getEncoder();

            string shareUserNameValue = shareUserName.Content.ToString();

            shareEncoderComboBox.Text = shareUserNameValue;
        }

        private async void save_Click(object sender, RoutedEventArgs e)
        {
            var DialogResult = MessageBox.Show("Are you sure you want to change?", "Change", MessageBoxButton.YesNo, MessageBoxImage.Question, MessageBoxResult.No);

            if (DialogResult == MessageBoxResult.Yes)
            {
                

                var url = "http://192.168.254.114:8080/smartcare/changeShare.php";

                var client = new HttpClient();

                var data = new Dictionary<string, string>{
                {"share_id", shareEncoderComboBox.SelectedItem.ToString()}
                };

                var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
                var content = await httpResponseMessage.Content.ReadAsStringAsync();

                if(content != "")
                {
                    MessageBox.Show("Changed Successfully");
                    shareUserName.Content = content;
                    shareLabel.Content = "Shared to:";
                }


                else
                {
                    MessageBox.Show("Changing Share failed.", "Sharing Error", MessageBoxButton.OK, MessageBoxImage.Error);

                }

                save.Visibility = Visibility.Hidden;
                cancel.Visibility = Visibility.Hidden;
                change.Visibility = Visibility.Visible;
                stopSharing.Visibility = Visibility.Visible;

                shareEncoderComboBox.Visibility = Visibility.Hidden;
                shareUserName.Visibility = Visibility.Visible;
            }
        }

        private void cancel_Click(object sender, RoutedEventArgs e)
        {
            save.Visibility = Visibility.Hidden;
            cancel.Visibility = Visibility.Hidden;
            change.Visibility = Visibility.Visible;
            stopSharing.Visibility = Visibility.Visible;

            shareEncoderComboBox.Visibility = Visibility.Hidden;
            shareUserName.Visibility = Visibility.Visible;
            shareLabel.Content = "Shared to:";

        }

        private void shareEncoderComboBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            //changeSelectedValue = ((System.Windows.Controls.ComboBoxItem)shareEncoderComboBox.SelectedItem).Content as string;
        }
    }
}
