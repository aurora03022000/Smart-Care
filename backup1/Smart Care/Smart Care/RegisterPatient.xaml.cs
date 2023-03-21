using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
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
    /// Interaction logic for RegisterPatient.xaml
    /// </summary>
    public partial class RegisterPatient : Window
    {
        string selectedGender;
        string selectedDevice;
        string selectedRoom;
        string selectedEncoder;

        string genderSubstitute = "empty";
        string deviceIDSubstitute = "empty";
        string roomSubstitute = "empty";
        string encoderSubstitute = "empty";

        public static RegisterPatient instance;
        public RegisterPatient()
        {
            InitializeComponent();

            getDeviceID();
            getRoom();
            getEncoder();
        }

        private void TypeNumericValidation(object sender, TextCompositionEventArgs e)
        {
            e.Handled = new Regex("[^0-9]+").IsMatch(e.Text);
        }

        private async void admitPatient_Button_Click(object sender, RoutedEventArgs e)
        {
            string name = patientName.Text;
            string gender = selectedGender;
            string birthdatestring = bdate.Text;
            string addressstring = address.Text;
            string numberstring = number.Text;
            string disease = patientDisease.Text;
            string deviceIDString = selectedDevice;
            string roomString = selectedRoom;
            string encoderString = selectedEncoder;

            if (name == "")
            {
                MessageBox.Show("Name field is required");
                patientName.Focus();
            }

            else if (genderSubstitute == "empty")
            {
                MessageBox.Show("Gender field is required");
            }

            else if (birthdatestring == "")
            {
                MessageBox.Show("Birthdate field is required");
                bdate.Focus();
            }

            else if (addressstring == "")
            {
                MessageBox.Show("Address field is required");
                address.Focus();
            }

            else if (numberstring == "")
            {
                MessageBox.Show("Number field is required");
                number.Focus();
            }

            else if (disease == "")
            {
                MessageBox.Show("Disease field is required");
                patientDisease.Focus();
            }

            else if (deviceIDSubstitute == "empty")
            {
                MessageBox.Show("Device ID field is required");
                deviceid.Items.Clear();
                getDeviceID();
            }

            else if (roomSubstitute == "empty")
            {
                MessageBox.Show("Room field is required");
                room.Items.Clear();
                getRoom();
            }

            else if (encoderSubstitute == "empty")
            {
                MessageBox.Show("Encoder field is required");
                encoder.Items.Clear();
                getEncoder();
            }

            else
            {
                //FOR CHECKING OF DEVICE ID

                var url = "http://192.168.254.114:8080/smartcare/checkDevice.php";

                var client = new HttpClient();

                var data = new Dictionary<string, string>{
                        { "device_id", deviceIDString}
                    };

                var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
                var content = await httpResponseMessage.Content.ReadAsStringAsync();

                if (content == "success")
                {
                    //FOR CHECKING OF ENCODER
                    var url1 = "http://192.168.254.114:8080/smartcare/checkRoom.php";

                    var client1 = new HttpClient();

                    var data1 = new Dictionary<string, string>{
                        { "room", roomString}
                    };

                    var httpResponseMessage1 = await client1.PostAsync(url1, new FormUrlEncodedContent(data1));
                    var content1 = await httpResponseMessage1.Content.ReadAsStringAsync();

                    if (content1 == "success")
                    {
                        //FOR CHECKING OF ROOM
                        var url2 = "http://192.168.254.114:8080/smartcare/checkEncoder.php";

                        var client2 = new HttpClient();

                        var data2 = new Dictionary<string, string>{
                        { "encoder", encoderString}
                        };

                        var httpResponseMessage2 = await client2.PostAsync(url2, new FormUrlEncodedContent(data2));
                        var content2 = await httpResponseMessage2.Content.ReadAsStringAsync();

                        if (content2 == "success")
                        {
                            //ADMITING PATIENT
                             var url3 = "http://192.168.254.114:8080/smartcare/register.php";

                             var client3 = new HttpClient();

                             var data3 = new Dictionary<string, string>{
                                     { "name", name},
                                     { "gender", selectedGender},
                                     { "number", numberstring},
                                     { "birthdate", birthdatestring},
                                     { "address", addressstring},
                                     { "disease", disease},
                                     { "deviceID", deviceIDString},
                                     { "room", roomString},
                                     { "encoder", encoderString}
                                 };

                             var httpResponseMessage3 = await client3.PostAsync(url3, new FormUrlEncodedContent(data3));
                             var content3 = await httpResponseMessage3.Content.ReadAsStringAsync();

                             if (content3 == "success")
                             {
                                 MessageBox.Show("Patient have been admitted successfully");
                                
                                patientName.Text = "";
                                genderPatient.SelectedIndex = -1;
                                bdate.Text = "";
                                address.Text = "";
                                number.Text = "";
                                patientDisease.Text = "";
                                deviceid.SelectedIndex = -1;
                                room.SelectedIndex = -1;
                                encoder.SelectedIndex = -1;

                                Home.instance.patientGrid.Items.Clear();
                                Home.instance.getAllPatientData();

                             }

                             else
                             {
                                 MessageBox.Show("Admittion failed.", "Admittion Error", MessageBoxButton.OK, MessageBoxImage.Error);
                             }

                            //ADMITING PATIENT

                        }

                        else if (content2 == "none")
                        {
                            MessageBox.Show("Encoder does not exist anymore.", "Encoder Error", MessageBoxButton.OK, MessageBoxImage.Error);
                            encoder.SelectedIndex = -1;
                            encoderSubstitute = "empty";
                            encoder.Items.Clear();
                            getEncoder();
                        }

                        else
                        {
                            MessageBox.Show("Checking Encoder Availability failed.", "Checking Error", MessageBoxButton.OK, MessageBoxImage.Error);
                        }

                        //FOR CHECKING OF ENCODER
                    }

                    else if (content1 == "none")
                    {
                        MessageBox.Show("Room is not available.", "Room Error", MessageBoxButton.OK, MessageBoxImage.Error);
                        room.SelectedIndex = -1;
                        roomSubstitute = "empty";
                        room.Items.Clear();
                        getRoom();
                    }

                    else
                    {
                        MessageBox.Show("Checking Room Availability failed.", "Checking Error", MessageBoxButton.OK, MessageBoxImage.Error);
                    }

                    //FOR CHECKING OF ROOM

                }

                else if (content == "none")
                {
                    MessageBox.Show("Device ID is not available.", "Device ID Error", MessageBoxButton.OK, MessageBoxImage.Error);
                    deviceid.SelectedIndex = -1;
                    deviceIDSubstitute = "empty";
                    deviceid.Items.Clear();
                    getDeviceID();
                }

                else
                {
                    MessageBox.Show("Checking Device Availability failed.", "Checking Error", MessageBoxButton.OK, MessageBoxImage.Error);
                }

                //FOR CHECKING OF DEVICE ID

            }
        }

        private void gender_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (genderPatient.SelectedIndex != -1)
            {
                selectedGender = ((System.Windows.Controls.ComboBoxItem)genderPatient.SelectedItem).Content as string;
            }

            genderSubstitute = "not empty";
        }

        private void deviceid_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (deviceid.SelectedIndex != -1)
            {
                selectedDevice = deviceid.SelectedItem.ToString();
            }

            deviceIDSubstitute = "not empty";
        }

        private void room_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (room.SelectedIndex != -1)
            {
                selectedRoom = room.SelectedItem.ToString();
            }

            roomSubstitute = "not empty";

        }

        private void encoder_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (encoder.SelectedIndex != -1)
            {
                selectedEncoder = encoder.SelectedItem.ToString();
            }

            encoderSubstitute = "not empty";
        }

        private void getDeviceID()
        {
            HttpClient client = new HttpClient();
            client.BaseAddress = new Uri("http://192.168.254.114:8080/smartcare/");
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));


            HttpResponseMessage response = client.GetAsync("getDeviceIDAdmin1.php").Result;

            int i = 0;

            if (response.IsSuccessStatusCode)
            {
                var deviceID = response.Content.ReadAsStringAsync().Result;

                for (; ; )
                {
                    if (deviceID == "" || deviceID == " ")
                    {
                        break;
                    }

                    else
                    {
                        string device_id = deviceID.Substring(0, deviceID.IndexOf("`"));
                        int device_id_length = device_id.Length;
                        int device_id_length1 = device_id_length + 1;
                        string finalNewList = deviceID.Remove(0, device_id_length1);

                        deviceID = finalNewList;

                        deviceid.Items.Insert(i, device_id);

                        i++;

                    }
                }
            }

            else
            {
                MessageBox.Show("Connection Failure.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void getRoom()
        {
            int f = 0;

            HttpClient client1 = new HttpClient();
            client1.BaseAddress = new Uri("http://192.168.254.114:8080/smartcare/");
            client1.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));


            HttpResponseMessage response1 = client1.GetAsync("getRoomAdmin1.php").Result;

            if (response1.IsSuccessStatusCode)
            {
                var roomNumber = response1.Content.ReadAsStringAsync().Result;

                for (; ; )
                {
                    if (roomNumber == "" || roomNumber == " ")
                    {
                        break;
                    }

                    else
                    {
                        string room_number = roomNumber.Substring(0, roomNumber.IndexOf("`"));
                        int room_number_length = room_number.Length;
                        int room_number_length1 = room_number_length + 1;
                        string finalNewList = roomNumber.Remove(0, room_number_length1);

                        roomNumber = finalNewList;

                        room.Items.Insert(f, room_number);

                        f++;
                    }
                }
            }

            else
            {
                MessageBox.Show("Connection Failure.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void getEncoder()
        {
            int e = 0;

            HttpClient client2 = new HttpClient();
            client2.BaseAddress = new Uri("http://192.168.254.114:8080/smartcare/");
            client2.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));


            HttpResponseMessage response2 = client2.GetAsync("getEncoderAdmin1.php").Result;

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

                        encoder.Items.Insert(e, staff_name);

                        e++;
                    }
                }
            }

            else
            {
                MessageBox.Show("Connection Failure.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }
    }
}
