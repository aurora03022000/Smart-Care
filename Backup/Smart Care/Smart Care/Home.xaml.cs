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
using System.Data;
using System.Text.RegularExpressions;
using System.IO;
using System.Diagnostics;
using Microsoft.Win32;

namespace Smart_Care
{
    /// <summary>
    /// Interaction logic for Home.xaml
    /// </summary>
    public partial class Home : Window
    {
        int clicked = 0;

        string staffGridComboBoxSelectedValue;
        string staffGridComboBoxSelectedValueGender;
        string patientGridComboBoxSelectedValue;
        string patientGridComboBoxSelectedValueGender;
        string selectedHistoryLogFilter;
        string deviceGridComboBoxSelectedValue;
        string deviceStatusComboBoxSelectedValue;
        string patientHistoryLogGridSelected;
        string patientHistoryGenderSelected;
        string staffHistorySelectedValueFilter;
        string staffHistorySelectedValueGender;
        string historyLogSelectedValueActivity;
        string historyLogSelectedValueTime;
        string selectedHealthHistoryFilter;
        string historyHealthLogSelectedValueTime;
        string roomSelectedValue;
        string roomSelectedStatus;
        string selectedCategory;
        public static Home instance;
        public string staffID1, patientID1, currentUserID, patientHealth1, patientShare1, historyOrNot;

        public struct staffData
        {
            public int id { set; get; }
            public string username { set; get; }
            public string staff_name { set; get; }
            public string dependent { set; get; }
            public string gender { set; get; }
            public string address { set; get; }
        }

        public struct patientData
        {
            public int patient_id { set; get; }
            public string patient_name { set; get; }
            public string dependent { set; get; }
            public string patient_address { set; get; }

            public string patient_gender { set; get; }
        }

        public struct deviceData
        {
            public int device_id { set; get; }
            public string device_status { set; get; }
            public string ipaddress { set; get; }
        }
        public struct historyLogData
        {
            public int history_id { set; get; }
            public string activity { set; get; }
            public string staff_name { set; get; }
            public string dateStamp { set; get; }
            public string timeStamp { set; get; }
        }

        public struct patientDataHistory
        {
            public int history_patient_id { set; get; }
            public string history_patient_name { set; get; }
            public string history_patient_gender { set; get; }
            public string history_patient_dependent { set; get; }
            public string history_date_admit { set; get; }
            public string history_date_dismiss { set; get; }
        }

        public struct historyStaffData
        {
            public int staff_id { set; get; }
            public string username { set; get; }
            public string staff_name { set; get; }
            public string gender { set; get; }
            public string dependent { set; get; }
            public string date_registered { set; get; }
            public string date_deleted { set; get; }

        }

        public struct historyHealthData
        {
            public int health_id { set; get; }
            public string patient_name { set; get; }
            public string date { set; get; }
            public string time { set; get; }
        }

        public struct roomData
        {
            public int room_number { set; get; }
            public string room_status { set; get; }
            public string current_user { set; get; }
        }


        public Home()
        {
            InitializeComponent();

            instance = this;

            getHospitalName();

            //hide the id column in datagrid
            medicalStaffGrid.Columns[0].MaxWidth = 0;
            patientGrid.Columns[0].MaxWidth = 0;
            historyGridTable.Columns[0].MaxWidth = 0;
            historyGridTablePatient.Columns[0].MaxWidth = 0;
            historyGridTableStaff.Columns[0].MaxWidth = 0;
            historyGridTableHealth.Columns[0].MaxWidth = 0;
            historyGridTable.Columns[0].MaxWidth = 0;

            staffComboBoxItem.SelectedIndex = 0;

            staff_button_txt.FontWeight = FontWeights.Bold;
            medicalStaffButton.BorderBrush = Brushes.White;
            medicalStaffButton.BorderThickness = new Thickness(1, 1, 1, 3);
            //medicalStaffButton.Background = (SolidColorBrush)new BrushConverter().ConvertFromString("#086012");

            getAllStaffData();
            getAllDeviceID();
        }


        private void home_button(object sender, RoutedEventArgs e)
        {
            staff_button_txt.FontWeight = FontWeights.Bold;
            medicalStaffButton.BorderBrush = Brushes.White;
            medicalStaffButton.BorderThickness = new Thickness(1, 1, 1, 3);
            //medicalStaffButton.Background = (SolidColorBrush)new BrushConverter().ConvertFromString("#086012");

            patient_button_txt.FontWeight = FontWeights.Normal;
            patientButton.BorderBrush = Brushes.Gray;
            patientButton.BorderThickness = new Thickness(1);

            medicalStaffGrid.Items.Clear();
            getAllStaffData();

            staffMainGrid.Visibility = Visibility.Visible;
            patientMainGrid.Visibility = Visibility.Hidden;

            homeGrid.Visibility = Visibility.Visible;
            deviceGrid.Visibility = Visibility.Hidden;
            historyGrid.Visibility = Visibility.Hidden;
            contactsGrid.Visibility = Visibility.Hidden;
            aboutGrid.Visibility = Visibility.Hidden;
            indexGrid.Visibility = Visibility.Hidden;
        }
        private void device_button(object sender, RoutedEventArgs e)
        {
            deviceCategory.Text = "Device ID";
            deviceDataGrid.Items.Clear();
            deviceidText.Text = "-";
            deviceStatusText.Text = "-";
            IPAddressText.Text = "-";
            currentUserText.Text = "-";
            currentRoomText.Text = "-";
            getAllDeviceID();
            homeGrid.Visibility = Visibility.Hidden;
            deviceGrid.Visibility = Visibility.Visible;
            historyGrid.Visibility = Visibility.Hidden;
            contactsGrid.Visibility = Visibility.Hidden;
            aboutGrid.Visibility = Visibility.Hidden;
            indexGrid.Visibility = Visibility.Hidden;

        }
        private void history_button(object sender, RoutedEventArgs e)
        {
            historyGridTable.Items.Clear();
            getAllHistoryLog();

            historyComboBox.SelectedIndex = 0;

            homeGrid.Visibility = Visibility.Hidden;
            deviceGrid.Visibility = Visibility.Hidden;
            historyGrid.Visibility = Visibility.Visible;
            contactsGrid.Visibility = Visibility.Hidden;
            aboutGrid.Visibility = Visibility.Hidden;
            indexGrid.Visibility = Visibility.Hidden;

        }
        private void contact_button(object sender, RoutedEventArgs e)
        {
            comboBoxRoom.Text = "Room Number";
            roomGrid.Items.Clear();
            getAllRoom();
            getHospitalInformation();

            homeGrid.Visibility = Visibility.Hidden;
            deviceGrid.Visibility = Visibility.Hidden;
            historyGrid.Visibility = Visibility.Hidden;
            contactsGrid.Visibility = Visibility.Visible;
            aboutGrid.Visibility = Visibility.Hidden;
            indexGrid.Visibility = Visibility.Hidden;

        }
        private void about_button(object sender, RoutedEventArgs e)
        {
            homeGrid.Visibility = Visibility.Hidden;
            deviceGrid.Visibility = Visibility.Hidden;
            historyGrid.Visibility = Visibility.Hidden;
            contactsGrid.Visibility = Visibility.Hidden;
            aboutGrid.Visibility = Visibility.Visible;
            indexGrid.Visibility = Visibility.Hidden;

        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            var DialogResult = MessageBox.Show("Are you sure you want to logout?", "Logout", MessageBoxButton.YesNo, MessageBoxImage.Question, MessageBoxResult.No);

            if (DialogResult == MessageBoxResult.Yes)
            {
                Login login = new Login();
                this.Close();
                login.Show();
            }
        }

        private void medicalStaffButton_Click(object sender, RoutedEventArgs e)
        {
            staffMainGrid.Visibility = Visibility.Visible;
            patientMainGrid.Visibility = Visibility.Hidden;

            // medicalStaffButton.Background = (SolidColorBrush)new BrushConverter().ConvertFromString("#086012");
            // patientButton.Background = (SolidColorBrush)new BrushConverter().ConvertFromString("#0EB021");

            staff_button_txt.FontWeight = FontWeights.Bold;
            medicalStaffButton.BorderBrush = Brushes.White;
            medicalStaffButton.BorderThickness = new Thickness(1, 1, 1, 3);

            patient_button_txt.FontWeight = FontWeights.Normal;
            patientButton.BorderBrush = Brushes.Gray;
            patientButton.BorderThickness = new Thickness(1);

            medicalStaffGrid.Items.Clear();
            getAllStaffData();
        }

        private void patientButton_Click(object sender, RoutedEventArgs e)
        {
            staffMainGrid.Visibility = Visibility.Hidden;
            patientMainGrid.Visibility = Visibility.Visible;

            //patientButton.Background = (SolidColorBrush)new BrushConverter().ConvertFromString("#086012");
            //medicalStaffButton.Background = (SolidColorBrush)new BrushConverter().ConvertFromString("#0EB021");

            staff_button_txt.FontWeight = FontWeights.Normal;
            medicalStaffButton.BorderBrush = Brushes.Gray;
            medicalStaffButton.BorderThickness = new Thickness(1);

            patient_button_txt.FontWeight = FontWeights.Bold;
            patientButton.BorderBrush = Brushes.White;
            patientButton.BorderThickness = new Thickness(1, 1, 1, 3);

            patientComboBoxItem.SelectedIndex = 0;

            patientGrid.Items.Clear();
            getAllPatientData();
        }
        private void btnView_Click(object sender, RoutedEventArgs e)
        {
            var selectedIndex = medicalStaffGrid.SelectedIndex;
            TextBlock x = medicalStaffGrid.Columns[0].GetCellContent(medicalStaffGrid.Items[selectedIndex]) as TextBlock;
            staffID1 = x.Text;
            historyOrNot = "no";

            /*var url = "http://192.168.254.114:8080/smartcare/update_current_clicked_user.php";

            var client = new HttpClient();

            var data = new Dictionary<string, string>
{
                {"user_id", staffID}
            };

            var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
            var content = await httpResponseMessage.Content.ReadAsStringAsync();*/


            foreach (Window w in Application.Current.Windows)
            {
                if (w is staffInformation)
                {
                    w.Close();

                    clicked = 0;
                }
            }

            if (clicked == 0)
            {
                staffInformation newwindow = new staffInformation();
                newwindow.Show();
            }

        }

        private async void btnDelete_Click(object sender, RoutedEventArgs e)
        {
            var DialogResult = MessageBox.Show("Are you sure you want to delete this Medical Staff?", "Delete Medical Staff", MessageBoxButton.YesNo, MessageBoxImage.Question, MessageBoxResult.No);

            if (DialogResult == MessageBoxResult.Yes)
            {
                var selectedIndex1 = medicalStaffGrid.SelectedIndex;
                TextBlock x1 = medicalStaffGrid.Columns[0].GetCellContent(medicalStaffGrid.Items[selectedIndex1]) as TextBlock;
                string staffID1 = x1.Text;

                var url = "http://192.168.254.114:8080/smartcare/removeStaff.php";

                var client = new HttpClient();

                var data = new Dictionary<string, string>{
                {"user_id", staffID1}
                };

                var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
                var content = await httpResponseMessage.Content.ReadAsStringAsync();

                if (content == "used")
                {
                    MessageBox.Show("Medical staff cannot be delete, in used", "Delete Error", MessageBoxButton.OK, MessageBoxImage.Error);
                }

                else if (content == "success")
                {
                    MessageBox.Show("Medical Staff deleted successfully");
                    medicalStaffGrid.Items.Clear();
                    getAllStaffData();
                }

                else
                {
                    MessageBox.Show("Delete operation error", "Delete Error", MessageBoxButton.OK, MessageBoxImage.Error);
                }

            }


        }

        private async void btnViewPatient_Click(object sender, RoutedEventArgs e)
        {
            var selectedIndexPatient = patientGrid.SelectedIndex;
            TextBlock xPatient = patientGrid.Columns[0].GetCellContent(patientGrid.Items[selectedIndexPatient]) as TextBlock;
            patientID1 = xPatient.Text;
            historyOrNot = "no";

            /*var url = "http://192.168.254.114:8080/smartcare/update_current_clicked_patient.php";

            var client = new HttpClient();

            var data = new Dictionary<string, string>{
                {"patient_id", patientID}
            };

            var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
            var content = await httpResponseMessage.Content.ReadAsStringAsync();*/

            foreach (Window w in Application.Current.Windows)
            {
                if (w is patientinformation)
                {
                    w.Close();

                    clicked = 0;
                }
            }

            if (clicked == 0)
            {
                patientinformation newwindowPatientInfo = new patientinformation();
                newwindowPatientInfo.Show();
            }

        }

        private async void btnSharePatient_Click(object sender, RoutedEventArgs e)
        {
            var selectedIndexPatient = patientGrid.SelectedIndex;
            TextBlock xPatient = patientGrid.Columns[0].GetCellContent(patientGrid.Items[selectedIndexPatient]) as TextBlock;
            patientShare1 = xPatient.Text;

            //patient Share Here

            /*var url = "http://192.168.254.114:8080/smartcare/update_current_clicked_patient.php";

            var client = new HttpClient();

            var data = new Dictionary<string, string>{
                {"patient_id", patientID}
            };

            var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
            var content = await httpResponseMessage.Content.ReadAsStringAsync();*/

            foreach (Window w in Application.Current.Windows)
            {
                if (w is sharePatient)
                {
                    w.Close();

                    clicked = 0;
                }
            }

            if (clicked == 0)
            {
                sharePatient newSharePatientWindow = new sharePatient();
                newSharePatientWindow.Show();
            }
        }

        private async void btnDeletePatient_Click(object sender, RoutedEventArgs e)
        {
            var DialogResult = MessageBox.Show("Are you sure you want to Dismiss this Patient?", "Dismiss Patient", MessageBoxButton.YesNo, MessageBoxImage.Question, MessageBoxResult.No);

            if (DialogResult == MessageBoxResult.Yes)
            {
                var selectedIndex1Patient = patientGrid.SelectedIndex;
                TextBlock x1Patient = patientGrid.Columns[0].GetCellContent(patientGrid.Items[selectedIndex1Patient]) as TextBlock;
                string patientID1 = x1Patient.Text;

                var url = "http://192.168.254.114:8080/smartcare/removePatientAdmin.php";

                var client = new HttpClient();

                var data = new Dictionary<string, string>{
                {"patient_id", patientID1}
                };

                var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
                var content = await httpResponseMessage.Content.ReadAsStringAsync();

                MessageBox.Show("Patient dismissed successfully");

                patientGrid.Items.Clear();
                getAllPatientData();
            }
        }

        public void getAllStaffData()
        {
            HttpClient client = new HttpClient();
            client.BaseAddress = new Uri("http://192.168.254.114:8080/smartcare/");
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));


            HttpResponseMessage response = client.GetAsync("getMedicalStaffInfoAdminSide.php").Result;
            if (response.IsSuccessStatusCode)
            {
                var medicalStaff = response.Content.ReadAsStringAsync().Result;

                for (; ; )
                {
                    if (medicalStaff == "" || medicalStaff == " ")
                    {
                        break;
                    }

                    else
                    {
                        string medicalStaff_id = medicalStaff.Substring(0, medicalStaff.IndexOf("`"));
                        int medicalStaff_id_int = Convert.ToInt32(medicalStaff_id);// returns int
                        int medicalStaff_id_length = medicalStaff_id.Length;
                        int medicalStaff_id_length1 = medicalStaff_id_length + 1;
                        string finalNewList = medicalStaff.Remove(0, medicalStaff_id_length1);

                        medicalStaff = finalNewList;

                        string medicalStaff_name = medicalStaff.Substring(0, medicalStaff.IndexOf("`"));
                        int medicalStaff_name_length = medicalStaff_name.Length;
                        int medicalStaff_name_length1 = medicalStaff_name_length + 1;
                        string finalNewList1 = medicalStaff.Remove(0, medicalStaff_name_length1);

                        medicalStaff = finalNewList1;

                        string medicalStaff_username = medicalStaff.Substring(0, medicalStaff.IndexOf("`"));
                        int medicalStaff_username_length = medicalStaff_username.Length;
                        int medicalStaff_username_length1 = medicalStaff_username_length + 1;
                        string finalNewList1a = medicalStaff.Remove(0, medicalStaff_username_length1);

                        medicalStaff = finalNewList1a;

                        string medicalStaff_dependent = medicalStaff.Substring(0, medicalStaff.IndexOf("`"));
                        int medicalStaff_dependent_length = medicalStaff_dependent.Length;
                        int medicalStaff_dependent_length1 = medicalStaff_dependent_length + 1;
                        string finalNewList1b = medicalStaff.Remove(0, medicalStaff_dependent_length1);

                        medicalStaff = finalNewList1b;

                        string medicalStaff_gender = medicalStaff.Substring(0, medicalStaff.IndexOf("`"));
                        int medicalStaff_gender_length = medicalStaff_gender.Length;
                        int medicalStaff_gender_length1 = medicalStaff_gender_length + 1;
                        string finalNewList2 = medicalStaff.Remove(0, medicalStaff_gender_length1);

                        medicalStaff = finalNewList2;

                        string medicalStaff_address = medicalStaff.Substring(0, medicalStaff.IndexOf("`"));
                        int medicalStaff_address_length = medicalStaff_address.Length;
                        int medicalStaff_address_length1 = medicalStaff_address_length + 1;
                        string finalNewList4 = medicalStaff.Remove(0, medicalStaff_address_length1);

                        medicalStaff = finalNewList4;

                        medicalStaffGrid.Items.Add(new staffData { id = medicalStaff_id_int, username = medicalStaff_username, staff_name = medicalStaff_name, dependent = medicalStaff_dependent, gender = medicalStaff_gender, address = medicalStaff_address });
                    }
                }
            }
            else
            {
                MessageBox.Show("Error Code" + response.StatusCode + " : Message - " + response.ReasonPhrase);
            }
        }

        public void getAllPatientData()
        {
            HttpClient client = new HttpClient();
            client.BaseAddress = new Uri("http://192.168.254.114:8080/smartcare/");
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));


            HttpResponseMessage response = client.GetAsync("getPatientInfoAdminSide.php").Result;
            if (response.IsSuccessStatusCode)
            {
                var patient = response.Content.ReadAsStringAsync().Result;

                for (; ; )
                {
                    if (patient == "" || patient == " ")
                    {
                        break;
                    }

                    else
                    {
                        string patientInfo_id = patient.Substring(0, patient.IndexOf("`"));
                        int patientInfo_id_int = Convert.ToInt32(patientInfo_id);// returns int
                        int patientInfo_id_length = patientInfo_id.Length;
                        int patientInfo_id_length1 = patientInfo_id_length + 1;
                        string finalNewList = patient.Remove(0, patientInfo_id_length1);

                        patient = finalNewList;

                        string patientInfo_name = patient.Substring(0, patient.IndexOf("`"));
                        int patientInfo_name_length = patientInfo_name.Length;
                        int patientInfo_name_length1 = patientInfo_name_length + 1;
                        string finalNewList1 = patient.Remove(0, patientInfo_name_length1);

                        patient = finalNewList1;

                        string patientInfo_gender = patient.Substring(0, patient.IndexOf("`"));
                        int patientInfo_gender_length = patientInfo_gender.Length;
                        int patientInfo_gender_length1 = patientInfo_gender_length + 1;
                        string finalNewList2 = patient.Remove(0, patientInfo_gender_length1);

                        patient = finalNewList2;

                        string patientInfo_dependent = patient.Substring(0, patient.IndexOf("`"));
                        int patientInfo_dependent_length = patientInfo_dependent.Length;
                        int patientInfo_dependent_length1 = patientInfo_dependent_length + 1;
                        string finalNewList2a = patient.Remove(0, patientInfo_dependent_length1);

                        patient = finalNewList2a;

                        string patientInfo_address = patient.Substring(0, patient.IndexOf("`"));
                        int patientInfo_address_length = patientInfo_address.Length;
                        int patientInfo_address_length1 = patientInfo_address_length + 1;
                        string finalNewList2b = patient.Remove(0, patientInfo_address_length1);

                        patient = finalNewList2b;

                        patientGrid.Items.Add(new patientData { patient_id = patientInfo_id_int, patient_name = patientInfo_name, patient_gender = patientInfo_gender, dependent = patientInfo_dependent, patient_address = patientInfo_address });
                    }
                }
            }
            else
            {
                MessageBox.Show("Error Code" + response.StatusCode + " : Message - " + response.ReasonPhrase);
            }
        }



        public void getAllDeviceID()
        {
            HttpClient client = new HttpClient();
            client.BaseAddress = new Uri("http://192.168.254.114:8080/smartcare/");
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));


            HttpResponseMessage response = client.GetAsync("getDevices.php").Result;
            if (response.IsSuccessStatusCode)
            {
                var device = response.Content.ReadAsStringAsync().Result;

                for (; ; )
                {
                    if (device == "" || device == " ")
                    {
                        break;
                    }

                    else
                    {
                        string deviceID = device.Substring(0, device.IndexOf("`"));
                        int deviceID_int = Convert.ToInt32(deviceID);// returns int
                        int deviceID_length = deviceID.Length;
                        int deviceID_length1 = deviceID_length + 1;
                        string finalNewList = device.Remove(0, deviceID_length1);

                        device = finalNewList;

                        string deviceStatus = device.Substring(0, device.IndexOf("`"));
                        int deviceStatus_length = deviceStatus.Length;
                        int deviceStatus_length1 = deviceStatus_length + 1;
                        string finalNewList1 = device.Remove(0, deviceStatus_length1);

                        device = finalNewList1;

                        string ipAddress = device.Substring(0, device.IndexOf("`"));
                        int ipAddress_length = ipAddress.Length;
                        int ipAddress_length1 = ipAddress_length + 1;
                        string finalNewList2 = device.Remove(0, ipAddress_length1);

                        device = finalNewList2;

                        deviceDataGrid.Items.Add(new deviceData { device_id = deviceID_int, device_status = deviceStatus, ipaddress = ipAddress });
                    }
                }
            }
            else
            {
                MessageBox.Show("Error Code" + response.StatusCode + " : Message - " + response.ReasonPhrase);
            }
        }

        private void getAllHistoryLog()
        {
            HttpClient client = new HttpClient();
            client.BaseAddress = new Uri("http://192.168.254.114:8080/smartcare/");
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));


            HttpResponseMessage response = client.GetAsync("getHistoryLog.php").Result;
            if (response.IsSuccessStatusCode)
            {
                var history = response.Content.ReadAsStringAsync().Result;

                for (; ; )
                {
                    if (history == "" || history == " ")
                    {
                        break;
                    }

                    else
                    {
                        string historyID = history.Substring(0, history.IndexOf("`"));
                        int historyID_int = Convert.ToInt32(historyID);// returns int
                        int historyID_length = historyID.Length;
                        int historyID_length1 = historyID_length + 1;
                        string finalNewList = history.Remove(0, historyID_length1);

                        history = finalNewList;

                        string history_staff_name = history.Substring(0, history.IndexOf("`"));
                        int history_staff_name_length = history_staff_name.Length;
                        int history_staff_name_length1 = history_staff_name_length + 1;
                        string finalNewList1 = history.Remove(0, history_staff_name_length1);

                        history = finalNewList1;

                        string historyDateStamp = history.Substring(0, history.IndexOf("`"));
                        int historyDateStamp_length = historyDateStamp.Length;
                        int historyDateStamp_length1 = historyDateStamp_length + 1;
                        string finalNewList2 = history.Remove(0, historyDateStamp_length1);

                        history = finalNewList2;

                        string historyTimeStamp = history.Substring(0, history.IndexOf("`"));
                        int historyTimeStamp_length = historyTimeStamp.Length;
                        int historyTimeStamp_length1 = historyTimeStamp_length + 1;
                        string finalNewList3 = history.Remove(0, historyTimeStamp_length1);

                        history = finalNewList3;

                        string historyActivity = history.Substring(0, history.IndexOf("`"));
                        int historyActivity_length = historyActivity.Length;
                        int historyActivity_length1 = historyActivity_length + 1;
                        string finalNewList4 = history.Remove(0, historyActivity_length1);

                        history = finalNewList4;

                        historyGridTable.Items.Add(new historyLogData { history_id = historyID_int, activity = historyActivity, staff_name = history_staff_name, dateStamp = historyDateStamp, timeStamp = historyTimeStamp });
                    }
                }
            }
            else
            {
                MessageBox.Show("Error Code" + response.StatusCode + " : Message - " + response.ReasonPhrase);
            }
        }

        private void getAllPatientHistory()
        {
            HttpClient client = new HttpClient();
            client.BaseAddress = new Uri("http://192.168.254.114:8080/smartcare/");
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));


            HttpResponseMessage response = client.GetAsync("getPatientHistory.php").Result;
            if (response.IsSuccessStatusCode)
            {
                var historyPatient = response.Content.ReadAsStringAsync().Result;

                for (; ; )
                {
                    if (historyPatient == "" || historyPatient == " ")
                    {
                        break;
                    }

                    else
                    {
                        string historyPatientID = historyPatient.Substring(0, historyPatient.IndexOf("`"));
                        int historyPatientID_int = Convert.ToInt32(historyPatientID);// returns int
                        int historyPatientID_length = historyPatientID.Length;
                        int historyPatientID_length1 = historyPatientID_length + 1;
                        string finalNewList = historyPatient.Remove(0, historyPatientID_length1);

                        historyPatient = finalNewList;

                        string historyPatientName = historyPatient.Substring(0, historyPatient.IndexOf("`"));
                        int historyPatientName_length = historyPatientName.Length;
                        int historyPatientName_length1 = historyPatientName_length + 1;
                        string finalNewList1 = historyPatient.Remove(0, historyPatientName_length1);

                        historyPatient = finalNewList1;

                        string historyPatientGender = historyPatient.Substring(0, historyPatient.IndexOf("`"));
                        int historyPatientGender_length = historyPatientGender.Length;
                        int historyPatientGender_length1 = historyPatientGender_length + 1;
                        string finalNewList2 = historyPatient.Remove(0, historyPatientGender_length1);

                        historyPatient = finalNewList2;

                        string historyPatientDependent = historyPatient.Substring(0, historyPatient.IndexOf("`"));
                        int historyPatientDependent_length = historyPatientDependent.Length;
                        int historyPatientDependent_length1 = historyPatientDependent_length + 1;
                        string finalNewList2a = historyPatient.Remove(0, historyPatientDependent_length1);

                        historyPatient = finalNewList2a;

                        string historyPatientDateAdmitted = historyPatient.Substring(0, historyPatient.IndexOf("`"));
                        int historyPatientDateAdmitted_length = historyPatientDateAdmitted.Length;
                        int historyPatientDateAdmitted_length1 = historyPatientDateAdmitted_length + 1;
                        string finalNewList3 = historyPatient.Remove(0, historyPatientDateAdmitted_length1);

                        historyPatient = finalNewList3;

                        string historyPatientDateDismissed = historyPatient.Substring(0, historyPatient.IndexOf("`"));
                        int historyPatientDateDismissed_length = historyPatientDateDismissed.Length;
                        int historyPatientDateDismissed_length1 = historyPatientDateDismissed_length + 1;
                        string finalNewList4 = historyPatient.Remove(0, historyPatientDateDismissed_length1);

                        historyPatient = finalNewList4;

                        historyGridTablePatient.Items.Add(new patientDataHistory { history_patient_id = historyPatientID_int, history_patient_name = historyPatientName, history_patient_gender = historyPatientGender, history_patient_dependent = historyPatientDependent, history_date_admit = historyPatientDateAdmitted, history_date_dismiss = historyPatientDateDismissed });
                    }
                }
            }
            else
            {
                MessageBox.Show("Error Code" + response.StatusCode + " : Message - " + response.ReasonPhrase);
            }
        }

        private void getAllStaffHistory()
        {
            HttpClient client = new HttpClient();
            client.BaseAddress = new Uri("http://192.168.254.114:8080/smartcare/");
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));


            HttpResponseMessage response = client.GetAsync("getStaffHistory.php").Result;
            if (response.IsSuccessStatusCode)
            {
                var historyStaff = response.Content.ReadAsStringAsync().Result;

                for (; ; )
                {
                    if (historyStaff == "" || historyStaff == " ")
                    {
                        break;
                    }

                    else
                    {
                        string historyStaffID = historyStaff.Substring(0, historyStaff.IndexOf("`"));
                        int historyStaffID_int = Convert.ToInt32(historyStaffID);// returns int
                        int historyStaffID_length = historyStaffID.Length;
                        int historyStaffID_length1 = historyStaffID_length + 1;
                        string finalNewList = historyStaff.Remove(0, historyStaffID_length1);

                        historyStaff = finalNewList;

                        string historyStaffUsername = historyStaff.Substring(0, historyStaff.IndexOf("`"));
                        int historyStaffUsername_length = historyStaffUsername.Length;
                        int historyStaffUsername_length1 = historyStaffUsername_length + 1;
                        string finalNewList1a = historyStaff.Remove(0, historyStaffUsername_length1);

                        historyStaff = finalNewList1a;

                        string historyStaffName = historyStaff.Substring(0, historyStaff.IndexOf("`"));
                        int historyStaffName_length = historyStaffName.Length;
                        int historyStaffName_length1 = historyStaffName_length + 1;
                        string finalNewList1 = historyStaff.Remove(0, historyStaffName_length1);

                        historyStaff = finalNewList1;

                        string historyStaffGender = historyStaff.Substring(0, historyStaff.IndexOf("`"));
                        int historyStaffGender_length = historyStaffGender.Length;
                        int historyStaffGender_length1 = historyStaffGender_length + 1;
                        string finalNewList2 = historyStaff.Remove(0, historyStaffGender_length1);

                        historyStaff = finalNewList2;

                        string historyStaffDependent = historyStaff.Substring(0, historyStaff.IndexOf("`"));
                        int historyStaffDependent_length = historyStaffDependent.Length;
                        int historyStaffDependent_length1 = historyStaffDependent_length + 1;
                        string finalNewList3 = historyStaff.Remove(0, historyStaffDependent_length1);

                        historyStaff = finalNewList3;

                        string historyStaffRegistered = historyStaff.Substring(0, historyStaff.IndexOf("`"));
                        int historyStaffRegistered_length = historyStaffRegistered.Length;
                        int historyStaffRegistered_length1 = historyStaffRegistered_length + 1;
                        string finalNewList3a = historyStaff.Remove(0, historyStaffRegistered_length1);

                        historyStaff = finalNewList3a;

                        string historyStaffDeleted = historyStaff.Substring(0, historyStaff.IndexOf("`"));
                        int historyStaffDeleted_length = historyStaffDeleted.Length;
                        int historyStaffDeleted_length1 = historyStaffDeleted_length + 1;
                        string finalNewList3b = historyStaff.Remove(0, historyStaffDeleted_length1);

                        historyStaff = finalNewList3b;

                        historyGridTableStaff.Items.Add(new historyStaffData { staff_id = historyStaffID_int, username = historyStaffUsername, staff_name = historyStaffName, gender = historyStaffGender, dependent = historyStaffDependent, date_registered = historyStaffRegistered, date_deleted = historyStaffDeleted });
                    }
                }
            }
            else
            {
                MessageBox.Show("Error Code" + response.StatusCode + " : Message - " + response.ReasonPhrase);
            }
        }


        private void getAllHealthHistory()
        {
            HttpClient client = new HttpClient();
            client.BaseAddress = new Uri("http://192.168.254.114:8080/smartcare/");
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));


            HttpResponseMessage response = client.GetAsync("getHealthHistory.php").Result;
            if (response.IsSuccessStatusCode)
            {
                var hsitoryHealth = response.Content.ReadAsStringAsync().Result;

                for (; ; )
                {
                    if (hsitoryHealth == "" || hsitoryHealth == " ")
                    {
                        break;
                    }

                    else
                    {
                        string historyHealthID = hsitoryHealth.Substring(0, hsitoryHealth.IndexOf("`"));
                        int historyHealthID_int = Convert.ToInt32(historyHealthID);// returns int
                        int historyHealthID_length = historyHealthID.Length;
                        int historyHealthID_length1 = historyHealthID_length + 1;
                        string finalNewList = hsitoryHealth.Remove(0, historyHealthID_length1);

                        hsitoryHealth = finalNewList;

                        string historyHealthPatientID = hsitoryHealth.Substring(0, hsitoryHealth.IndexOf("`"));
                        int historyHealthPatientID_length = historyHealthPatientID.Length;
                        int historyHealthPatientID_length1 = historyHealthPatientID_length + 1;
                        string finalNewList1 = hsitoryHealth.Remove(0, historyHealthPatientID_length1);

                        hsitoryHealth = finalNewList1;

                        string historyHealthDate = hsitoryHealth.Substring(0, hsitoryHealth.IndexOf("`"));
                        int historyHealthDate_length = historyHealthDate.Length;
                        int historyHealthDate_length1 = historyHealthDate_length + 1;
                        string finalNewList2 = hsitoryHealth.Remove(0, historyHealthDate_length1);

                        hsitoryHealth = finalNewList2;

                        string historyHealthTime = hsitoryHealth.Substring(0, hsitoryHealth.IndexOf("`"));
                        int historyHealthTime_length = historyHealthTime.Length;
                        int historyHealthTime_length1 = historyHealthTime_length + 1;
                        string finalNewList3 = hsitoryHealth.Remove(0, historyHealthTime_length1);

                        hsitoryHealth = finalNewList3;

                        historyGridTableHealth.Items.Add(new historyHealthData { health_id = historyHealthID_int, patient_name = historyHealthPatientID, date = historyHealthDate, time = historyHealthTime });
                    }
                }
            }
            else
            {
                MessageBox.Show("Error Code" + response.StatusCode + " : Message - " + response.ReasonPhrase);
            }
        }

        private async void btnDeleteDevice_Click(object sender, RoutedEventArgs e)
        {
            var DialogResult = MessageBox.Show("Are you sure you want to delete this Device?", "Delete Device", MessageBoxButton.YesNo, MessageBoxImage.Question, MessageBoxResult.No);

            if (DialogResult == MessageBoxResult.Yes)
            {
                var selectedIndexDevice = deviceDataGrid.SelectedIndex;
                TextBlock xDevice1 = deviceDataGrid.Columns[0].GetCellContent(deviceDataGrid.Items[selectedIndexDevice]) as TextBlock;
                string deviceID2 = xDevice1.Text;

                var url = "http://192.168.254.114:8080/smartcare/removeDevice.php";

                var client = new HttpClient();

                var data = new Dictionary<string, string>{
                {"device_id", deviceID2}
                };

                var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
                var content = await httpResponseMessage.Content.ReadAsStringAsync();

                if (content == "used")
                {
                    MessageBox.Show("Device cannot be delete, it is in used", "Delete Error", MessageBoxButton.OK, MessageBoxImage.Error);
                }

                else if (content == "success")
                {
                    MessageBox.Show("Device deleted successfully");
                    deviceDataGrid.Items.Clear();
                    getAllDeviceID();
                }

                else
                {
                    MessageBox.Show("Delete operation error", "Delete Error", MessageBoxButton.OK, MessageBoxImage.Error);
                }

            }
        }

        private async void btnViewDevice_Click(object sender, RoutedEventArgs e)
        {
            var selectedIndexDevice = deviceDataGrid.SelectedIndex;
            TextBlock xDevice = deviceDataGrid.Columns[0].GetCellContent(deviceDataGrid.Items[selectedIndexDevice]) as TextBlock;
            string deviceID1 = xDevice.Text;

            var url = "http://192.168.254.114:8080/smartcare/getPatientAndRoomOfDevice.php";

            var client = new HttpClient();

            var data = new Dictionary<string, string>
{
                {"device_id", deviceID1}
            };

            var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
            var content = await httpResponseMessage.Content.ReadAsStringAsync();

            string result = content.ToString();

            if (result != "" || result != " ")
            {
                if (result == "none")
                {
                    deviceidText.Text = "-";
                    deviceStatusText.Text = "-";
                    IPAddressText.Text = "-";
                    currentUserText.Text = "-";
                    currentRoomText.Text = "-";
                }

                else
                {

                    string deviceID = result.Substring(0, result.IndexOf("`"));
                    int deviceID_length = deviceID.Length;
                    int deviceID_length1 = deviceID_length + 1;
                    string finalNewList = result.Remove(0, deviceID_length1);

                    result = finalNewList;

                    string deviceStatus = result.Substring(0, result.IndexOf("`"));
                    int deviceStatus_length = deviceStatus.Length;
                    int deviceStatus_length1 = deviceStatus_length + 1;
                    string finalNewList1 = result.Remove(0, deviceStatus_length1);

                    result = finalNewList1;

                    string deviceIP = result.Substring(0, result.IndexOf("`"));
                    int deviceIP_length = deviceIP.Length;
                    int deviceIP_length1 = deviceIP_length + 1;
                    string finalNewList2 = result.Remove(0, deviceIP_length1);

                    result = finalNewList2;

                    string deviceUser = result.Substring(0, result.IndexOf("`"));
                    int deviceUser_length = deviceUser.Length;
                    int deviceUsere_length1 = deviceUser_length + 1;
                    string finalNewList3 = result.Remove(0, deviceUsere_length1);

                    result = finalNewList3;

                    string deviceRoom = finalNewList3;

                    deviceidText.Text = deviceID;
                    deviceStatusText.Text = deviceStatus;
                    IPAddressText.Text = deviceIP;
                    currentUserText.Text = deviceUser;
                    currentRoomText.Text = deviceRoom;
                }
            }

            else
            {
                MessageBox.Show("Connection failure");
            }
        }

        private void btnViewHistoryLog_Click(object sender, RoutedEventArgs e)
        {
            var selectedIndexHistoryLog = historyGridTable.SelectedIndex;
            TextBlock xHistoryLog = historyGridTable.Columns[0].GetCellContent(historyGridTable.Items[selectedIndexHistoryLog]) as TextBlock;
            string historyLogID1 = xHistoryLog.Text;

            MessageBox.Show(historyLogID1);
        }

        /*private async void btnDeleteHistoryLog_Click(object sender, RoutedEventArgs e)
        {
            var DialogResult = MessageBox.Show("Are you sure you want to delete this History Log?", "Delete History Log", MessageBoxButton.YesNo, MessageBoxImage.Question, MessageBoxResult.No);

            if (DialogResult == MessageBoxResult.Yes)
            {
                var selectedIndexHistoryLogDelete = historyGridTable.SelectedIndex;
                TextBlock xHistoryLogDelete = historyGridTable.Columns[0].GetCellContent(historyGridTable.Items[selectedIndexHistoryLogDelete]) as TextBlock;
                string historyLogDeleteID = xHistoryLogDelete.Text;

                var url = "http://192.168.254.114:8080/smartcare/removeHistoryLog.php";

                var client = new HttpClient();

                var data = new Dictionary<string, string>{
                {"history_id", historyLogDeleteID}
                };

                var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
                var content = await httpResponseMessage.Content.ReadAsStringAsync();

                historyGridTable.Items.Clear();
                getAllHistoryLog();
            }
        }*/

        private async void btnViewPatientHistory_Click(object sender, RoutedEventArgs e)
        {
            var selectedIndexHistoryPatientLog = historyGridTablePatient.SelectedIndex;
            TextBlock xHistoryPatientLog = historyGridTablePatient.Columns[0].GetCellContent(historyGridTablePatient.Items[selectedIndexHistoryPatientLog]) as TextBlock;
            patientID1 = xHistoryPatientLog.Text;
            historyOrNot = "yes";

            /*var url = "http://192.168.254.114:8080/smartcare/update_current_clicked_patient.php";

            var client = new HttpClient();

            var data = new Dictionary<string, string>{
                {"patient_id", historyLogPatientID1}
            };

            var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
            var content = await httpResponseMessage.Content.ReadAsStringAsync();*/

            foreach (Window w in Application.Current.Windows)
            {
                if (w is patientinformation)
                {
                    w.Close();

                    clicked = 0;
                }
            }

            if (clicked == 0)
            {
                patientinformation newwindowPatientInfo = new patientinformation();
                newwindowPatientInfo.Show();
            }


        }
        private void Register_Button(object sender, RoutedEventArgs e)
        {
            RegisterDevice homepage = new RegisterDevice();
            homepage.Show();
        }

        private void historyComboBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            selectedCategory = ((System.Windows.Controls.ComboBoxItem)historyComboBox.SelectedItem).Content as string;
            if (selectedCategory != null)
            {
                if (selectedCategory == "History Log")
                {
                    patientID.Visibility = Visibility.Visible;
                    staff_id.Visibility = Visibility.Visible;
                    healthID.Visibility = Visibility.Visible;
                    log_id.Visibility = Visibility.Visible;

                    historyLogFilter.Text = "Activity";

                    filterActivity.Text = "Admitted Patient";
                    searchDateHistoryLog.Text = "";
                    searchPatientNameActivity.Text = "";
                    timeFilterHistoryLog.Text = "12:00 am";

                    historyLogFilters.Visibility = Visibility.Visible;
                    healthFilters.Visibility = Visibility.Hidden;
                    patientHistoryFiltersGrid.Visibility = Visibility.Hidden;
                    medicalStaffHistoryFiltersGrid.Visibility = Visibility.Hidden;

                    historyGridTable.Items.Clear();
                    getAllHistoryLog();
                    historyGridTable.Visibility = Visibility.Visible;
                    historyGridTablePatient.Visibility = Visibility.Hidden;
                    historyGridTableStaff.Visibility = Visibility.Hidden;
                    historyGridTableHealth.Visibility = Visibility.Hidden;
                }

                else if (selectedCategory == "Patient Health Logs")
                {

                    filterComboBox.Text = "Patient Name";

                    patientID.Visibility = Visibility.Visible;
                    staff_id.Visibility = Visibility.Visible;
                    healthID.Visibility = Visibility.Visible;
                    log_id.Visibility = Visibility.Visible;

                    searchPatientNameHealth.Text = "";
                    searchDateHealth.Text = "Male";
                    timeFilter.Text = "12:00 am";

                    historyLogFilters.Visibility = Visibility.Hidden;
                    healthFilters.Visibility = Visibility.Visible;
                    patientHistoryFiltersGrid.Visibility = Visibility.Hidden;
                    medicalStaffHistoryFiltersGrid.Visibility = Visibility.Hidden;

                    historyGridTableHealth.Items.Clear();
                    getAllHealthHistory();
                    historyGridTable.Visibility = Visibility.Hidden;
                    historyGridTablePatient.Visibility = Visibility.Hidden;
                    historyGridTableStaff.Visibility = Visibility.Hidden;
                    historyGridTableHealth.Visibility = Visibility.Visible;
                }

                else if (selectedCategory == "Patient History")
                {

                    patientID.Visibility = Visibility.Visible;
                    staff_id.Visibility = Visibility.Visible;
                    healthID.Visibility = Visibility.Visible;
                    log_id.Visibility = Visibility.Visible;

                    patientHistoryFilter.Text = "Patient Name";

                    historyLogFilters.Visibility = Visibility.Hidden;
                    healthFilters.Visibility = Visibility.Hidden;
                    patientHistoryFiltersGrid.Visibility = Visibility.Visible;
                    medicalStaffHistoryFiltersGrid.Visibility = Visibility.Hidden;

                    historyGridTablePatient.Items.Clear();
                    getAllPatientHistory();
                    historyGridTable.Visibility = Visibility.Hidden;
                    historyGridTablePatient.Visibility = Visibility.Visible;
                    historyGridTableStaff.Visibility = Visibility.Hidden;
                    historyGridTableHealth.Visibility = Visibility.Hidden;
                }

                else if (selectedCategory == "Medical Staff History")
                {
                    historyGridTableHealth.Items.Clear();

                    patientID.Visibility = Visibility.Visible;
                    staff_id.Visibility = Visibility.Visible;
                    healthID.Visibility = Visibility.Visible;
                    log_id.Visibility = Visibility.Visible;

                    medicalStaffComboBoxFilter.Text = "Username";
                    searcStaffUsernameTextBox.Text = "";
                    filterStaffHistoryGender.Text = "Male";
                    searchBirthDateStaffHistory.Text = "";
                    searchStaffHistoryTextBoxNumber.Text = "";

                    historyGridTableStaff.Items.Clear();

                    historyLogFilters.Visibility = Visibility.Hidden;
                    healthFilters.Visibility = Visibility.Hidden;
                    patientHistoryFiltersGrid.Visibility = Visibility.Hidden;
                    medicalStaffHistoryFiltersGrid.Visibility = Visibility.Visible;

                    historyGridTableHealth.Items.Clear();
                    getAllStaffHistory();
                    historyGridTable.Visibility = Visibility.Hidden;
                    historyGridTablePatient.Visibility = Visibility.Hidden;
                    historyGridTableStaff.Visibility = Visibility.Visible;
                    historyGridTableHealth.Visibility = Visibility.Hidden;


                }
            }
        }

        private void staffComboBoxItem_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            search.Text = "";
            datePickerSearch.Text = "";
            number.Text = "";

            staffGridComboBoxSelectedValue = ((System.Windows.Controls.ComboBoxItem)staffComboBoxItem.SelectedItem).Content as string;

            if (staffGridComboBoxSelectedValue != null)
            {
                if (staffGridComboBoxSelectedValue == "Gender")
                {
                    genderComboBoxItem.Text = "Male";
                    genderComboBoxItem.Visibility = Visibility.Visible;
                    search.Visibility = Visibility.Hidden;
                    datePickerSearch.Visibility = Visibility.Hidden;
                    number.Visibility = Visibility.Hidden;
                }

                else if (staffGridComboBoxSelectedValue == "Birthdate")
                {
                    genderComboBoxItem.Visibility = Visibility.Hidden;
                    search.Visibility = Visibility.Hidden;
                    datePickerSearch.Visibility = Visibility.Visible;
                    number.Visibility = Visibility.Hidden;
                }

                else if (staffGridComboBoxSelectedValue == "Number")
                {
                    genderComboBoxItem.Visibility = Visibility.Hidden;
                    search.Visibility = Visibility.Hidden;
                    datePickerSearch.Visibility = Visibility.Hidden;
                    number.Visibility = Visibility.Visible;
                }

                else if (staffGridComboBoxSelectedValue == "Date Registered")
                {
                    genderComboBoxItem.Visibility = Visibility.Hidden;
                    search.Visibility = Visibility.Hidden;
                    datePickerSearch.Visibility = Visibility.Visible;
                    number.Visibility = Visibility.Hidden;
                }

                else
                {
                    genderComboBoxItem.Visibility = Visibility.Hidden;
                    datePickerSearch.Visibility = Visibility.Hidden;
                    number.Visibility = Visibility.Hidden;
                    search.Visibility = Visibility.Visible;
                }
            }
        }

        private void genderComboBoxItem_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            staffGridComboBoxSelectedValueGender = ((System.Windows.Controls.ComboBoxItem)genderComboBoxItem.SelectedItem).Content as string;
        }

        private async void searchButton_Click(object sender, RoutedEventArgs e)
        {


            var url = "http://192.168.254.114:8080/smartcare/searchFilter.php";

            var client = new HttpClient();

            string searchValue;

            if (staffGridComboBoxSelectedValue == "Gender")
            {
                searchValue = staffGridComboBoxSelectedValueGender;
            }

            else if (staffGridComboBoxSelectedValue == "Birthdate" || staffGridComboBoxSelectedValue == "Date Registered")
            {
                searchValue = datePickerSearch.Text;
            }

            else if (staffGridComboBoxSelectedValue == "Number")
            {
                searchValue = number.Text;
            }

            else
            {
                searchValue = search.Text.ToString();
            }

            var data = new Dictionary<string, string> { { "selectedValue", staffGridComboBoxSelectedValue }, { "searchValue", searchValue } };

            var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
            var content = await httpResponseMessage.Content.ReadAsStringAsync();

            string medicalStaff = content.ToString();

            medicalStaffGrid.Items.Clear();

            int count = 0;

            for (; ; )
            {
                if (medicalStaff == "" || medicalStaff == " ")
                {
                    break;
                }

                else
                {
                    if (count == 0)
                    {
                        if (staffGridComboBoxSelectedValue == "Birthdate")
                        {
                            birthdateColumn.Visibility = Visibility.Visible;
                            numberColumn.Visibility = Visibility.Hidden;
                            registeredColumn.Visibility = Visibility.Hidden;
                        }

                        else if (staffGridComboBoxSelectedValue == "Number")
                        {
                            birthdateColumn.Visibility = Visibility.Hidden;
                            numberColumn.Visibility = Visibility.Visible;
                            registeredColumn.Visibility = Visibility.Hidden;
                        }

                        else if (staffGridComboBoxSelectedValue == "Date Registered")
                        {
                            birthdateColumn.Visibility = Visibility.Hidden;
                            numberColumn.Visibility = Visibility.Hidden;
                            registeredColumn.Visibility = Visibility.Visible;
                        }
                    }
                    count++;
                    string medicalStaff_id = medicalStaff.Substring(0, medicalStaff.IndexOf("`"));
                    int medicalStaff_id_int = Convert.ToInt32(medicalStaff_id);// returns int
                    int medicalStaff_id_length = medicalStaff_id.Length;
                    int medicalStaff_id_length1 = medicalStaff_id_length + 1;
                    string finalNewList = medicalStaff.Remove(0, medicalStaff_id_length1);

                    medicalStaff = finalNewList;

                    string medicalStaff_name = medicalStaff.Substring(0, medicalStaff.IndexOf("`"));
                    int medicalStaff_name_length = medicalStaff_name.Length;
                    int medicalStaff_name_length1 = medicalStaff_name_length + 1;
                    string finalNewList1 = medicalStaff.Remove(0, medicalStaff_name_length1);

                    medicalStaff = finalNewList1;

                    string medicalStaff_username = medicalStaff.Substring(0, medicalStaff.IndexOf("`"));
                    int medicalStaff_username_length = medicalStaff_username.Length;
                    int medicalStaff_username_length1 = medicalStaff_username_length + 1;
                    string finalNewList1a = medicalStaff.Remove(0, medicalStaff_username_length1);

                    medicalStaff = finalNewList1a;

                    string medicalStaff_dependent = medicalStaff.Substring(0, medicalStaff.IndexOf("`"));
                    int medicalStaff_dependent_length = medicalStaff_dependent.Length;
                    int medicalStaff_dependent_length1 = medicalStaff_dependent_length + 1;
                    string finalNewList1b = medicalStaff.Remove(0, medicalStaff_dependent_length1);

                    medicalStaff = finalNewList1b;

                    string medicalStaff_gender = medicalStaff.Substring(0, medicalStaff.IndexOf("`"));
                    int medicalStaff_gender_length = medicalStaff_gender.Length;
                    int medicalStaff_gender_length1 = medicalStaff_gender_length + 1;
                    string finalNewList2 = medicalStaff.Remove(0, medicalStaff_gender_length1);

                    medicalStaff = finalNewList2;

                    string medicalStaff_address = medicalStaff.Substring(0, medicalStaff.IndexOf("`"));
                    int medicalStaff_address_length = medicalStaff_address.Length;
                    int medicalStaff_address_length1 = medicalStaff_address_length + 1;
                    string finalNewList4 = medicalStaff.Remove(0, medicalStaff_address_length1);

                    medicalStaff = finalNewList4;

                    medicalStaffGrid.Items.Add(new staffData { id = medicalStaff_id_int, username = medicalStaff_username, staff_name = medicalStaff_name, dependent = medicalStaff_dependent, gender = medicalStaff_gender, address = medicalStaff_address });
                }
            }
        }

        private void RegisterStaff_Click(object sender, RoutedEventArgs e)
        {
            bool isWindowOpen = false;

            foreach (Window w in Application.Current.Windows)
            {
                if (w is RegisterStaff)
                {
                    isWindowOpen = true;
                    w.Activate();
                }
            }

            if (!isWindowOpen)
            {
                RegisterStaff registerStaffWindow = new RegisterStaff();
                registerStaffWindow.Show();
            }
        }

        private void TypeNumericValidation(object sender, TextCompositionEventArgs e)
        {
            //e.Handled = new Regex("[^0-9]+").IsMatch(e.Text);
            bool approvedDecimalPoint = false;

            if (e.Text == ".")
            {
                if (!((TextBox)sender).Text.Contains("."))
                    approvedDecimalPoint = true;
            }

            if (!(char.IsDigit(e.Text, e.Text.Length - 1) || approvedDecimalPoint))
                e.Handled = true;
        }

        private async void searchButtonPatient_Click(object sender, RoutedEventArgs e)
        {
            var url = "http://192.168.254.114:8080/smartcare/searchFilterPatient.php";

            var client = new HttpClient();

            string searchValuePatient;

            if (patientGridComboBoxSelectedValue == "Gender")
            {
                searchValuePatient = patientGridComboBoxSelectedValueGender;
            }

            else if (patientGridComboBoxSelectedValue == "Birthdate" || patientGridComboBoxSelectedValue == "Date Admitted")
            {
                searchValuePatient = datePickerSearchPatient.Text;
            }

            else if (patientGridComboBoxSelectedValue == "Number" || patientGridComboBoxSelectedValue == "Device ID" || patientGridComboBoxSelectedValue == "Room")
            {
                searchValuePatient = numberPatient.Text;
            }

            else
            {
                searchValuePatient = searchPatient.Text.ToString();
            }

            var data = new Dictionary<string, string> { { "selectedValue", patientGridComboBoxSelectedValue }, { "searchValue", searchValuePatient } };

            var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
            var content = await httpResponseMessage.Content.ReadAsStringAsync();

            string patient = content.ToString();

            patientGrid.Items.Clear();

            int count = 0;

            for (; ; )
            {
                if (patient == "" || patient == " ")
                {
                    break;
                }

                else
                {
                    if (count == 0)
                    {
                        if (patientGridComboBoxSelectedValue == "Birthdate")
                        {
                            patientBirthdateColumn.Visibility = Visibility.Visible;
                            patientNumberColumn.Visibility = Visibility.Hidden;
                            patientDiseaseColumn.Visibility = Visibility.Hidden;
                            patientDeviceIDColumn.Visibility = Visibility.Hidden;
                            patientRoomColumn.Visibility = Visibility.Hidden;
                            patientAdmitColumn.Visibility = Visibility.Hidden;
                        }

                        else if (patientGridComboBoxSelectedValue == "Number")
                        {
                            patientBirthdateColumn.Visibility = Visibility.Hidden;
                            patientNumberColumn.Visibility = Visibility.Visible;
                            patientDiseaseColumn.Visibility = Visibility.Hidden;
                            patientDeviceIDColumn.Visibility = Visibility.Hidden;
                            patientRoomColumn.Visibility = Visibility.Hidden;
                            patientAdmitColumn.Visibility = Visibility.Hidden;
                        }

                        else if (patientGridComboBoxSelectedValue == "Disease")
                        {
                            patientBirthdateColumn.Visibility = Visibility.Hidden;
                            patientNumberColumn.Visibility = Visibility.Hidden;
                            patientDiseaseColumn.Visibility = Visibility.Visible;
                            patientDeviceIDColumn.Visibility = Visibility.Hidden;
                            patientRoomColumn.Visibility = Visibility.Hidden;
                            patientAdmitColumn.Visibility = Visibility.Hidden;
                        }

                        else if (patientGridComboBoxSelectedValue == "Device ID")
                        {
                            patientBirthdateColumn.Visibility = Visibility.Hidden;
                            patientNumberColumn.Visibility = Visibility.Hidden;
                            patientDiseaseColumn.Visibility = Visibility.Hidden;
                            patientDeviceIDColumn.Visibility = Visibility.Visible;
                            patientRoomColumn.Visibility = Visibility.Hidden;
                            patientAdmitColumn.Visibility = Visibility.Hidden;
                        }

                        else if (patientGridComboBoxSelectedValue == "Room")
                        {
                            patientBirthdateColumn.Visibility = Visibility.Hidden;
                            patientNumberColumn.Visibility = Visibility.Hidden;
                            patientDiseaseColumn.Visibility = Visibility.Hidden;
                            patientDeviceIDColumn.Visibility = Visibility.Hidden;
                            patientRoomColumn.Visibility = Visibility.Visible;
                            patientAdmitColumn.Visibility = Visibility.Hidden;
                        }

                        else if (patientGridComboBoxSelectedValue == "Date Admitted")
                        {
                            patientBirthdateColumn.Visibility = Visibility.Hidden;
                            patientNumberColumn.Visibility = Visibility.Hidden;
                            patientDiseaseColumn.Visibility = Visibility.Hidden;
                            patientDeviceIDColumn.Visibility = Visibility.Hidden;
                            patientRoomColumn.Visibility = Visibility.Hidden;
                            patientAdmitColumn.Visibility = Visibility.Visible;
                        }
                    }
                    count++;

                    string patientInfo_id = patient.Substring(0, patient.IndexOf("`"));
                    int patientInfo_id_int = Convert.ToInt32(patientInfo_id);// returns int
                    int patientInfo_id_length = patientInfo_id.Length;
                    int patientInfo_id_length1 = patientInfo_id_length + 1;
                    string finalNewList = patient.Remove(0, patientInfo_id_length1);

                    patient = finalNewList;

                    string patientInfo_name = patient.Substring(0, patient.IndexOf("`"));
                    int patientInfo_name_length = patientInfo_name.Length;
                    int patientInfo_name_length1 = patientInfo_name_length + 1;
                    string finalNewList1 = patient.Remove(0, patientInfo_name_length1);

                    patient = finalNewList1;

                    string patientInfo_gender = patient.Substring(0, patient.IndexOf("`"));
                    int patientInfo_gender_length = patientInfo_gender.Length;
                    int patientInfo_gender_length1 = patientInfo_gender_length + 1;
                    string finalNewList2 = patient.Remove(0, patientInfo_gender_length1);

                    patient = finalNewList2;

                    string patientInfo_dependent = patient.Substring(0, patient.IndexOf("`"));
                    int patientInfo_dependent_length = patientInfo_dependent.Length;
                    int patientInfo_dependent_length1 = patientInfo_dependent_length + 1;
                    string finalNewList2a = patient.Remove(0, patientInfo_dependent_length1);

                    patient = finalNewList2a;

                    string patientInfo_address = patient.Substring(0, patient.IndexOf("`"));
                    int patientInfo_address_length = patientInfo_address.Length;
                    int patientInfo_address_length1 = patientInfo_address_length + 1;
                    string finalNewList2b = patient.Remove(0, patientInfo_address_length1);

                    patient = finalNewList2b;

                    patientGrid.Items.Add(new patientData { patient_id = patientInfo_id_int, patient_name = patientInfo_name, patient_gender = patientInfo_gender, dependent = patientInfo_dependent, patient_address = patientInfo_address });
                }
            }
        }

        private void patientComboBoxItem_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            searchPatient.Text = "";
            datePickerSearchPatient.Text = "";
            numberPatient.Text = "";

            patientGridComboBoxSelectedValue = ((System.Windows.Controls.ComboBoxItem)patientComboBoxItem.SelectedItem).Content as string;

            if (patientGridComboBoxSelectedValue != null)
            {
                if (patientGridComboBoxSelectedValue == "Gender")
                {
                    genderComboBoxItemPatient.Text = "Male";
                    genderComboBoxItemPatient.Visibility = Visibility.Visible;
                    searchPatient.Visibility = Visibility.Hidden;
                    datePickerSearchPatient.Visibility = Visibility.Hidden;
                    numberPatient.Visibility = Visibility.Hidden;
                }

                else if (patientGridComboBoxSelectedValue == "Birthdate")
                {
                    genderComboBoxItemPatient.Visibility = Visibility.Hidden;
                    searchPatient.Visibility = Visibility.Hidden;
                    datePickerSearchPatient.Visibility = Visibility.Visible;
                    numberPatient.Visibility = Visibility.Hidden;
                }

                else if (patientGridComboBoxSelectedValue == "Number")
                {
                    genderComboBoxItemPatient.Visibility = Visibility.Hidden;
                    searchPatient.Visibility = Visibility.Hidden;
                    datePickerSearchPatient.Visibility = Visibility.Hidden;
                    numberPatient.Visibility = Visibility.Visible;
                }

                else if (patientGridComboBoxSelectedValue == "Device ID")
                {
                    genderComboBoxItemPatient.Visibility = Visibility.Hidden;
                    searchPatient.Visibility = Visibility.Hidden;
                    datePickerSearchPatient.Visibility = Visibility.Hidden;
                    numberPatient.Visibility = Visibility.Visible;
                }

                else if (patientGridComboBoxSelectedValue == "Room")
                {
                    genderComboBoxItemPatient.Visibility = Visibility.Hidden;
                    searchPatient.Visibility = Visibility.Hidden;
                    datePickerSearchPatient.Visibility = Visibility.Hidden;
                    numberPatient.Visibility = Visibility.Visible;
                }

                else if (patientGridComboBoxSelectedValue == "Room")
                {
                    genderComboBoxItemPatient.Visibility = Visibility.Hidden;
                    searchPatient.Visibility = Visibility.Hidden;
                    datePickerSearchPatient.Visibility = Visibility.Hidden;
                    numberPatient.Visibility = Visibility.Visible;
                }

                else if (patientGridComboBoxSelectedValue == "Date Admitted")
                {
                    genderComboBoxItemPatient.Visibility = Visibility.Hidden;
                    searchPatient.Visibility = Visibility.Hidden;
                    datePickerSearchPatient.Visibility = Visibility.Visible;
                    numberPatient.Visibility = Visibility.Hidden;
                }

                else
                {
                    genderComboBoxItemPatient.Visibility = Visibility.Hidden;
                    searchPatient.Visibility = Visibility.Visible;
                    datePickerSearchPatient.Visibility = Visibility.Hidden;
                    numberPatient.Visibility = Visibility.Hidden;
                }
            }
        }

        private void genderComboBoxItemPatient_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            patientGridComboBoxSelectedValueGender = ((System.Windows.Controls.ComboBoxItem)genderComboBoxItemPatient.SelectedItem).Content as string;
        }

        private void admit_patient_button_Button_Click(object sender, RoutedEventArgs e)
        {
            bool isWindowOpen1 = false;

            foreach (Window w in Application.Current.Windows)
            {
                if (w is RegisterPatient)
                {
                    isWindowOpen1 = true;
                    w.Activate();
                }
            }

            if (!isWindowOpen1)
            {
                RegisterPatient registerPatientWindow = new RegisterPatient();
                registerPatientWindow.Show();
            }
        }

        private void filterComboBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            selectedHealthHistoryFilter = ((System.Windows.Controls.ComboBoxItem)filterComboBox.SelectedItem).Content as string;


            if (selectedHealthHistoryFilter == "Patient Name")
            {
                searchPatientNameHealth.Text = "";
                searchDateHealth.Text = "";
                timeFilter.Text = "12:00 am";


                searchPatientNameHealth.Visibility = Visibility.Visible;
                searchDateHealth.Visibility = Visibility.Hidden;
                timeFilter.Visibility = Visibility.Hidden;
            }

            else if (selectedHealthHistoryFilter == "Date Encoded")
            {
                searchPatientNameHealth.Text = "";
                searchDateHealth.Text = "";
                timeFilter.Text = "12:00 am";

                searchPatientNameHealth.Visibility = Visibility.Hidden;
                searchDateHealth.Visibility = Visibility.Visible;
                timeFilter.Visibility = Visibility.Hidden;
            }

            else if (selectedHealthHistoryFilter == "Time Encoded")
            {
                searchPatientNameHealth.Text = "";
                searchDateHealth.Text = "";
                timeFilter.Text = "12:00 am";

                searchPatientNameHealth.Visibility = Visibility.Hidden;
                searchPatientNameHealth.Visibility = Visibility.Hidden;
                searchDateHealth.Visibility = Visibility.Hidden;
                timeFilter.Visibility = Visibility.Visible;
            }

        }

        private void historyLogFilter_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            selectedHistoryLogFilter = ((System.Windows.Controls.ComboBoxItem)historyLogFilter.SelectedItem).Content as string;

            if (selectedHistoryLogFilter == "Activity")
            {
                filterActivity.Text = "All Activity";
                searchDateHistoryLog.Text = "";
                timeFilterHistoryLog.Text = "12:00 am";
                searchPatientNameActivity.Text = "";

                filterActivity.Visibility = Visibility.Visible;
                searchDateHistoryLog.Visibility = Visibility.Hidden;
                timeFilterHistoryLog.Visibility = Visibility.Hidden;
                searchPatientNameActivity.Visibility = Visibility.Hidden;

                searchButtonHistoryLog.Margin = new Thickness(657, 35, -64, 0);
            }

            else if (selectedHistoryLogFilter == "Medical Staff Name")
            {
                filterActivity.Text = "All Activity";
                searchDateHistoryLog.Text = "";
                timeFilterHistoryLog.Text = "12:00 am";
                searchPatientNameActivity.Text = "";

                filterActivity.Visibility = Visibility.Hidden;
                searchDateHistoryLog.Visibility = Visibility.Hidden;
                timeFilterHistoryLog.Visibility = Visibility.Hidden;
                searchPatientNameActivity.Visibility = Visibility.Visible;

                searchButtonHistoryLog.Margin = new Thickness(593, 35, 0, 0);
            }

            else if (selectedHistoryLogFilter == "Activity Date")
            {
                filterActivity.Text = "All Activity";
                searchDateHistoryLog.Text = "";
                timeFilterHistoryLog.Text = "12:00 am";
                searchPatientNameActivity.Text = "";

                filterActivity.Visibility = Visibility.Hidden;
                searchDateHistoryLog.Visibility = Visibility.Visible;
                timeFilterHistoryLog.Visibility = Visibility.Hidden;
                searchPatientNameActivity.Visibility = Visibility.Hidden;

                searchButtonHistoryLog.Margin = new Thickness(593, 35, 0, 0);
            }

            else if (selectedHistoryLogFilter == "Activity Time")
            {
                filterActivity.Text = "All Activity";
                searchDateHistoryLog.Text = "";
                timeFilterHistoryLog.Text = "12:00 am";
                searchPatientNameActivity.Text = "";

                filterActivity.Visibility = Visibility.Hidden;
                searchDateHistoryLog.Visibility = Visibility.Hidden;
                timeFilterHistoryLog.Visibility = Visibility.Visible;
                searchPatientNameActivity.Visibility = Visibility.Hidden;

                searchButtonHistoryLog.Margin = new Thickness(593, 35, 0, 0);
            }
        }

        private void patientHistoryFilter_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            patientHistoryLogGridSelected = ((System.Windows.Controls.ComboBoxItem)patientHistoryFilter.SelectedItem).Content as string;


            if (patientHistoryLogGridSelected == "Patient Name")
            {
                searchHistoryTextBox.Visibility = Visibility.Visible;
                filterPatientHistoryGender.Visibility = Visibility.Hidden;
                searchBirthDatePatientHistory.Visibility = Visibility.Hidden;
                searchHistoryTextBoxNumber.Visibility = Visibility.Hidden;

                searchHistoryTextBox.Text = "";
                filterPatientHistoryGender.Text = "Male";
                searchBirthDatePatientHistory.Text = "";
                searchHistoryTextBoxNumber.Text = "";
            }

            else if (patientHistoryLogGridSelected == "Gender")
            {
                searchHistoryTextBox.Visibility = Visibility.Hidden;
                filterPatientHistoryGender.Visibility = Visibility.Visible;
                searchBirthDatePatientHistory.Visibility = Visibility.Hidden;
                searchHistoryTextBoxNumber.Visibility = Visibility.Hidden;

                searchHistoryTextBox.Text = "";
                filterPatientHistoryGender.Text = "Male";
                searchBirthDatePatientHistory.Text = "";
                searchHistoryTextBoxNumber.Text = "";
            }

            else if (patientHistoryLogGridSelected == "Birthdate")
            {
                searchHistoryTextBox.Visibility = Visibility.Hidden;
                filterPatientHistoryGender.Visibility = Visibility.Hidden;
                searchBirthDatePatientHistory.Visibility = Visibility.Visible;
                searchHistoryTextBoxNumber.Visibility = Visibility.Hidden;

                searchHistoryTextBox.Text = "";
                filterPatientHistoryGender.Text = "Male";
                searchBirthDatePatientHistory.Text = "";
                searchHistoryTextBoxNumber.Text = "";
            }

            else if (patientHistoryLogGridSelected == "Address")
            {
                searchHistoryTextBox.Visibility = Visibility.Visible;
                filterPatientHistoryGender.Visibility = Visibility.Hidden;
                searchBirthDatePatientHistory.Visibility = Visibility.Hidden;
                searchHistoryTextBoxNumber.Visibility = Visibility.Hidden;

                searchHistoryTextBox.Text = "";
                filterPatientHistoryGender.Text = "Male";
                searchBirthDatePatientHistory.Text = "";
                searchHistoryTextBoxNumber.Text = "";
            }

            else if (patientHistoryLogGridSelected == "Number")
            {
                searchHistoryTextBox.Visibility = Visibility.Hidden;
                filterPatientHistoryGender.Visibility = Visibility.Hidden;
                searchBirthDatePatientHistory.Visibility = Visibility.Hidden;
                searchHistoryTextBoxNumber.Visibility = Visibility.Visible;

                searchHistoryTextBox.Text = "";
                filterPatientHistoryGender.Text = "Male";
                searchBirthDatePatientHistory.Text = "";
                searchHistoryTextBoxNumber.Text = "";
            }

            else if (patientHistoryLogGridSelected == "Disease")
            {
                searchHistoryTextBox.Visibility = Visibility.Visible;
                filterPatientHistoryGender.Visibility = Visibility.Hidden;
                searchBirthDatePatientHistory.Visibility = Visibility.Hidden;
                searchHistoryTextBoxNumber.Visibility = Visibility.Hidden;

                searchHistoryTextBox.Text = "";
                filterPatientHistoryGender.Text = "Male";
                searchBirthDatePatientHistory.Text = "";
                searchHistoryTextBoxNumber.Text = "";
            }

            else if (patientHistoryLogGridSelected == "Device ID")
            {
                searchHistoryTextBox.Visibility = Visibility.Hidden;
                filterPatientHistoryGender.Visibility = Visibility.Hidden;
                searchBirthDatePatientHistory.Visibility = Visibility.Hidden;
                searchHistoryTextBoxNumber.Visibility = Visibility.Visible;

                searchHistoryTextBox.Text = "";
                filterPatientHistoryGender.Text = "Male";
                searchBirthDatePatientHistory.Text = "";
                searchHistoryTextBoxNumber.Text = "";
            }

            else if (patientHistoryLogGridSelected == "Room")
            {
                searchHistoryTextBox.Visibility = Visibility.Hidden;
                filterPatientHistoryGender.Visibility = Visibility.Hidden;
                searchBirthDatePatientHistory.Visibility = Visibility.Hidden;
                searchHistoryTextBoxNumber.Visibility = Visibility.Visible;

                searchHistoryTextBox.Text = "";
                filterPatientHistoryGender.Text = "Male";
                searchBirthDatePatientHistory.Text = "";
                searchHistoryTextBoxNumber.Text = "";
            }

            else if (patientHistoryLogGridSelected == "Encoder Name")
            {
                searchHistoryTextBox.Visibility = Visibility.Visible;
                filterPatientHistoryGender.Visibility = Visibility.Hidden;
                searchBirthDatePatientHistory.Visibility = Visibility.Hidden;
                searchHistoryTextBoxNumber.Visibility = Visibility.Hidden;

                searchHistoryTextBox.Text = "";
                filterPatientHistoryGender.Text = "Male";
                searchBirthDatePatientHistory.Text = "";
                searchHistoryTextBoxNumber.Text = "";
            }

            else if (patientHistoryLogGridSelected == "Date Admitted")
            {
                searchHistoryTextBox.Visibility = Visibility.Hidden;
                filterPatientHistoryGender.Visibility = Visibility.Hidden;
                searchBirthDatePatientHistory.Visibility = Visibility.Visible;
                searchHistoryTextBoxNumber.Visibility = Visibility.Hidden;

                searchHistoryTextBox.Text = "";
                filterPatientHistoryGender.Text = "Male";
                searchBirthDatePatientHistory.Text = "";
                searchHistoryTextBoxNumber.Text = "";
            }

            else if (patientHistoryLogGridSelected == "Date Dismissed")
            {
                searchHistoryTextBox.Visibility = Visibility.Hidden;
                filterPatientHistoryGender.Visibility = Visibility.Hidden;
                searchBirthDatePatientHistory.Visibility = Visibility.Visible;
                searchHistoryTextBoxNumber.Visibility = Visibility.Hidden;

                searchHistoryTextBox.Text = "";
                filterPatientHistoryGender.Text = "Male";
                searchBirthDatePatientHistory.Text = "";
                searchHistoryTextBoxNumber.Text = "";
            }
        }

        private void deviceCategory_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            deviceGridComboBoxSelectedValue = ((System.Windows.Controls.ComboBoxItem)deviceCategory.SelectedItem).Content as string;

            if (deviceGridComboBoxSelectedValue == "Device ID")
            {
                deviceIDTextBox.Text = "";
                statusComboBox.Text = "Available";
                deviveIPTextBox.Text = "";

                deviceIDTextBox.Visibility = Visibility.Visible;
                statusComboBox.Visibility = Visibility.Hidden;
                deviveIPTextBox.Visibility = Visibility.Hidden;
            }

            else if (deviceGridComboBoxSelectedValue == "Status")
            {
                deviceIDTextBox.Text = "";
                statusComboBox.Text = "Available";
                deviveIPTextBox.Text = "";

                deviceIDTextBox.Visibility = Visibility.Hidden;
                statusComboBox.Visibility = Visibility.Visible;
                deviveIPTextBox.Visibility = Visibility.Hidden;
            }

            else if (deviceGridComboBoxSelectedValue == "IP Addresses")
            {
                deviceIDTextBox.Text = "";
                statusComboBox.Text = "Available";
                deviveIPTextBox.Text = "";

                deviceIDTextBox.Visibility = Visibility.Hidden;
                statusComboBox.Visibility = Visibility.Hidden;
                deviveIPTextBox.Visibility = Visibility.Visible;
            }
        }

        private async void searchButtonDevice_Click(object sender, RoutedEventArgs e)
        {
            string searchValueDevice;

            if (deviceGridComboBoxSelectedValue == "Device ID")
            {
                searchValueDevice = deviceIDTextBox.Text;
            }

            else if (deviceGridComboBoxSelectedValue == "Status")
            {
                searchValueDevice = deviceStatusComboBoxSelectedValue;
            }

            else if (deviceGridComboBoxSelectedValue == "IP Addresses")
            {
                searchValueDevice = deviveIPTextBox.Text;

            }

            else
            {
                searchValueDevice = "";
            }

            var url = "http://192.168.254.114:8080/smartcare/searchDevice.php";

            var client = new HttpClient();

            var data = new Dictionary<string, string>
            {
                {"searchDeviceFilter", searchValueDevice},
                {"searchDeviceCategory", deviceGridComboBoxSelectedValue}
            };

            var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
            var content = await httpResponseMessage.Content.ReadAsStringAsync();

            string deviceSearchResult = content.ToString();
            deviceDataGrid.Items.Clear();

            for (; ; )
            {
                if (deviceSearchResult == "" || deviceSearchResult == " ")
                {
                    break;
                }

                else
                {

                    string deviceID = deviceSearchResult.Substring(0, deviceSearchResult.IndexOf("`"));
                    int deviceID_int = Convert.ToInt32(deviceID);// returns int
                    int deviceID_length = deviceID.Length;
                    int deviceID_length1 = deviceID_length + 1;
                    string finalNewList = deviceSearchResult.Remove(0, deviceID_length1);

                    deviceSearchResult = finalNewList;

                    string deviceStatus = deviceSearchResult.Substring(0, deviceSearchResult.IndexOf("`"));
                    int deviceStatus_length = deviceStatus.Length;
                    int deviceStatus_length1 = deviceStatus_length + 1;
                    string finalNewList1 = deviceSearchResult.Remove(0, deviceStatus_length1);

                    deviceSearchResult = finalNewList1;

                    string ipAddress = deviceSearchResult.Substring(0, deviceSearchResult.IndexOf("`"));
                    int ipAddress_length = ipAddress.Length;
                    int ipAddress_length1 = ipAddress_length + 1;
                    string finalNewList2 = deviceSearchResult.Remove(0, ipAddress_length1);

                    deviceSearchResult = finalNewList2;

                    deviceDataGrid.Items.Add(new deviceData { device_id = deviceID_int, device_status = deviceStatus, ipaddress = ipAddress });
                }
            }
        }

        private void statusComboBox_SelectionChanged_1(object sender, SelectionChangedEventArgs e)
        {
            deviceStatusComboBoxSelectedValue = ((System.Windows.Controls.ComboBoxItem)statusComboBox.SelectedItem).Content as string;
        }

        private void registerDeviceBtn_Click(object sender, RoutedEventArgs e)
        {
            bool isWindowOpen1 = false;

            foreach (Window w in Application.Current.Windows)
            {
                if (w is RegisterDevice)
                {
                    isWindowOpen1 = true;
                    w.Activate();
                }
            }

            if (!isWindowOpen1)
            {
                RegisterDevice registerDeviceWindow = new RegisterDevice();
                registerDeviceWindow.Show();
            }
        }

        private void filterPatientHistoryRoom_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {

        }

        private void filterPatientHistoryDeviceID_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {

        }

        private void filterPatientHistoryGender_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            patientHistoryGenderSelected = ((System.Windows.Controls.ComboBoxItem)filterPatientHistoryGender.SelectedItem).Content as string;
        }

        private async void searchButtonPatientHistory_Click(object sender, RoutedEventArgs e)
        {
            string patientHistorySearchValue;

            if (patientHistoryLogGridSelected == "Patient Name")
            {
                patientHistorySearchValue = searchHistoryTextBox.Text;
            }

            else if (patientHistoryLogGridSelected == "Gender")
            {
                patientHistorySearchValue = patientHistoryGenderSelected;
            }

            else if (patientHistoryLogGridSelected == "Birthdate")
            {
                patientHistorySearchValue = searchBirthDatePatientHistory.Text;

                address_dependent.Visibility = Visibility.Hidden;
                birthdate_dependent.Visibility = Visibility.Visible;
                number_dependent.Visibility = Visibility.Hidden;
                disease_dependent.Visibility = Visibility.Hidden;
                deviceID_dependent.Visibility = Visibility.Hidden;
                room_dependent.Visibility = Visibility.Hidden;
                encoder_dependent.Visibility = Visibility.Hidden;
            }

            else if (patientHistoryLogGridSelected == "Address")
            {
                patientHistorySearchValue = searchHistoryTextBox.Text;

                address_dependent.Visibility = Visibility.Visible;
                birthdate_dependent.Visibility = Visibility.Hidden;
                number_dependent.Visibility = Visibility.Hidden;
                disease_dependent.Visibility = Visibility.Hidden;
                deviceID_dependent.Visibility = Visibility.Hidden;
                room_dependent.Visibility = Visibility.Hidden;
                encoder_dependent.Visibility = Visibility.Hidden;
            }

            else if (patientHistoryLogGridSelected == "Number")
            {
                patientHistorySearchValue = searchHistoryTextBoxNumber.Text;

                address_dependent.Visibility = Visibility.Hidden;
                birthdate_dependent.Visibility = Visibility.Hidden;
                number_dependent.Visibility = Visibility.Visible;
                disease_dependent.Visibility = Visibility.Hidden;
                deviceID_dependent.Visibility = Visibility.Hidden;
                room_dependent.Visibility = Visibility.Hidden;
                encoder_dependent.Visibility = Visibility.Hidden;
            }

            else if (patientHistoryLogGridSelected == "Disease")
            {
                patientHistorySearchValue = searchHistoryTextBox.Text;

                address_dependent.Visibility = Visibility.Hidden;
                birthdate_dependent.Visibility = Visibility.Hidden;
                number_dependent.Visibility = Visibility.Hidden;
                disease_dependent.Visibility = Visibility.Visible;
                deviceID_dependent.Visibility = Visibility.Hidden;
                room_dependent.Visibility = Visibility.Hidden;
                encoder_dependent.Visibility = Visibility.Hidden;
            }

            else if (patientHistoryLogGridSelected == "Device ID")
            {
                patientHistorySearchValue = searchHistoryTextBoxNumber.Text;

                address_dependent.Visibility = Visibility.Hidden;
                birthdate_dependent.Visibility = Visibility.Hidden;
                number_dependent.Visibility = Visibility.Hidden;
                disease_dependent.Visibility = Visibility.Hidden;
                deviceID_dependent.Visibility = Visibility.Visible;
                room_dependent.Visibility = Visibility.Hidden;
                encoder_dependent.Visibility = Visibility.Hidden;
            }

            else if (patientHistoryLogGridSelected == "Room")
            {
                patientHistorySearchValue = searchHistoryTextBoxNumber.Text;

                address_dependent.Visibility = Visibility.Hidden;
                birthdate_dependent.Visibility = Visibility.Hidden;
                number_dependent.Visibility = Visibility.Hidden;
                disease_dependent.Visibility = Visibility.Hidden;
                deviceID_dependent.Visibility = Visibility.Hidden;
                room_dependent.Visibility = Visibility.Visible;
                encoder_dependent.Visibility = Visibility.Hidden;
            }

            else if (patientHistoryLogGridSelected == "Encoder Name")
            {
                patientHistorySearchValue = searchHistoryTextBox.Text;

                address_dependent.Visibility = Visibility.Hidden;
                birthdate_dependent.Visibility = Visibility.Hidden;
                number_dependent.Visibility = Visibility.Hidden;
                disease_dependent.Visibility = Visibility.Hidden;
                deviceID_dependent.Visibility = Visibility.Hidden;
                room_dependent.Visibility = Visibility.Hidden;
                encoder_dependent.Visibility = Visibility.Visible;
            }

            else if (patientHistoryLogGridSelected == "Date Admitted")
            {
                patientHistorySearchValue = searchBirthDatePatientHistory.Text;
            }

            else if (patientHistoryLogGridSelected == "Date Dismissed")
            {
                patientHistorySearchValue = searchBirthDatePatientHistory.Text;
            }

            else
            {
                patientHistorySearchValue = "";
            }


            var url = "http://192.168.254.114:8080/smartcare/searchPatientHistory.php";

            var client = new HttpClient();

            var data = new Dictionary<string, string>
            {
                {"selectedValue", patientHistoryLogGridSelected},
                {"searchValue", patientHistorySearchValue}
            };

            var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
            var content = await httpResponseMessage.Content.ReadAsStringAsync();

            string patientSearchHistoryResult = content.ToString();
            historyGridTablePatient.Items.Clear();

            var historyPatient = patientSearchHistoryResult;

            for (; ; )
            {
                if (historyPatient == "" || historyPatient == " ")
                {
                    break;
                }

                else
                {
                    string historyPatientID = historyPatient.Substring(0, historyPatient.IndexOf("`"));
                    int historyPatientID_int = Convert.ToInt32(historyPatientID);// returns int
                    int historyPatientID_length = historyPatientID.Length;
                    int historyPatientID_length1 = historyPatientID_length + 1;
                    string finalNewList = historyPatient.Remove(0, historyPatientID_length1);

                    historyPatient = finalNewList;

                    string historyPatientName = historyPatient.Substring(0, historyPatient.IndexOf("`"));
                    int historyPatientName_length = historyPatientName.Length;
                    int historyPatientName_length1 = historyPatientName_length + 1;
                    string finalNewList1 = historyPatient.Remove(0, historyPatientName_length1);

                    historyPatient = finalNewList1;

                    string historyPatientGender = historyPatient.Substring(0, historyPatient.IndexOf("`"));
                    int historyPatientGender_length = historyPatientGender.Length;
                    int historyPatientGender_length1 = historyPatientGender_length + 1;
                    string finalNewList2 = historyPatient.Remove(0, historyPatientGender_length1);

                    historyPatient = finalNewList2;

                    string historyPatientDependent = historyPatient.Substring(0, historyPatient.IndexOf("`"));
                    int historyPatientDependent_length = historyPatientDependent.Length;
                    int historyPatientDependent_length1 = historyPatientDependent_length + 1;
                    string finalNewList2a = historyPatient.Remove(0, historyPatientDependent_length1);

                    historyPatient = finalNewList2a;

                    string historyPatientDateAdmitted = historyPatient.Substring(0, historyPatient.IndexOf("`"));
                    int historyPatientDateAdmitted_length = historyPatientDateAdmitted.Length;
                    int historyPatientDateAdmitted_length1 = historyPatientDateAdmitted_length + 1;
                    string finalNewList3 = historyPatient.Remove(0, historyPatientDateAdmitted_length1);

                    historyPatient = finalNewList3;

                    string historyPatientDateDismissed = historyPatient.Substring(0, historyPatient.IndexOf("`"));
                    int historyPatientDateDismissed_length = historyPatientDateDismissed.Length;
                    int historyPatientDateDismissed_length1 = historyPatientDateDismissed_length + 1;
                    string finalNewList4 = historyPatient.Remove(0, historyPatientDateDismissed_length1);

                    historyPatient = finalNewList4;

                    historyGridTablePatient.Items.Add(new patientDataHistory { history_patient_id = historyPatientID_int, history_patient_name = historyPatientName, history_patient_gender = historyPatientGender, history_patient_dependent = historyPatientDependent, history_date_admit = historyPatientDateAdmitted, history_date_dismiss = historyPatientDateDismissed });
                }
            }
        }

        private void medicalStaffComboBoxFilter_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            staffHistorySelectedValueFilter = ((System.Windows.Controls.ComboBoxItem)medicalStaffComboBoxFilter.SelectedItem).Content as string;

            searcStaffUsernameTextBox.Text = "";
            filterStaffHistoryGender.Text = "Male";
            searchBirthDateStaffHistory.Text = "";
            searchStaffHistoryTextBoxNumber.Text = "";

            if (staffHistorySelectedValueFilter == "Username")
            {
                searcStaffUsernameTextBox.Visibility = Visibility.Visible;
                filterStaffHistoryGender.Visibility = Visibility.Hidden;
                searchBirthDateStaffHistory.Visibility = Visibility.Hidden;
                searchStaffHistoryTextBoxNumber.Visibility = Visibility.Hidden;
            }

            else if (staffHistorySelectedValueFilter == "Medical Staff Name")
            {
                searcStaffUsernameTextBox.Visibility = Visibility.Visible;
                filterStaffHistoryGender.Visibility = Visibility.Hidden;
                searchBirthDateStaffHistory.Visibility = Visibility.Hidden;
                searchStaffHistoryTextBoxNumber.Visibility = Visibility.Hidden;
            }

            else if (staffHistorySelectedValueFilter == "Gender")
            {
                searcStaffUsernameTextBox.Visibility = Visibility.Hidden;
                filterStaffHistoryGender.Visibility = Visibility.Visible;
                searchBirthDateStaffHistory.Visibility = Visibility.Hidden;
                searchStaffHistoryTextBoxNumber.Visibility = Visibility.Hidden;
            }

            else if (staffHistorySelectedValueFilter == "Birthdate")
            {
                searcStaffUsernameTextBox.Visibility = Visibility.Hidden;
                filterStaffHistoryGender.Visibility = Visibility.Hidden;
                searchBirthDateStaffHistory.Visibility = Visibility.Visible;
                searchStaffHistoryTextBoxNumber.Visibility = Visibility.Hidden;
            }

            else if (staffHistorySelectedValueFilter == "Address")
            {
                searcStaffUsernameTextBox.Visibility = Visibility.Visible;
                filterStaffHistoryGender.Visibility = Visibility.Hidden;
                searchBirthDateStaffHistory.Visibility = Visibility.Hidden;
                searchStaffHistoryTextBoxNumber.Visibility = Visibility.Hidden;
            }

            else if (staffHistorySelectedValueFilter == "Number")
            {
                searcStaffUsernameTextBox.Visibility = Visibility.Hidden;
                filterStaffHistoryGender.Visibility = Visibility.Hidden;
                searchBirthDateStaffHistory.Visibility = Visibility.Hidden;
                searchStaffHistoryTextBoxNumber.Visibility = Visibility.Visible;
            }

            else if (staffHistorySelectedValueFilter == "Date Registered")
            {
                searcStaffUsernameTextBox.Visibility = Visibility.Hidden;
                filterStaffHistoryGender.Visibility = Visibility.Hidden;
                searchBirthDateStaffHistory.Visibility = Visibility.Visible;
                searchStaffHistoryTextBoxNumber.Visibility = Visibility.Hidden;
            }

            else if (staffHistorySelectedValueFilter == "Date Deleted")
            {
                searcStaffUsernameTextBox.Visibility = Visibility.Hidden;
                filterStaffHistoryGender.Visibility = Visibility.Hidden;
                searchBirthDateStaffHistory.Visibility = Visibility.Visible;
                searchStaffHistoryTextBoxNumber.Visibility = Visibility.Hidden;
            }
        }

        private async void searchButtonStaffHistory_Click(object sender, RoutedEventArgs e)
        {
            string searchValueMedicalStaff;

            if (staffHistorySelectedValueFilter == "Username")
            {
                staff_address_dependent.Visibility = Visibility.Visible;
                staff_birthdate_dependent.Visibility = Visibility.Hidden;
                staff_number_dependent.Visibility = Visibility.Hidden;


                searchValueMedicalStaff = searcStaffUsernameTextBox.Text;
            }

            else if (staffHistorySelectedValueFilter == "Medical Staff Name")
            {
                staff_address_dependent.Visibility = Visibility.Visible;
                staff_birthdate_dependent.Visibility = Visibility.Hidden;
                staff_number_dependent.Visibility = Visibility.Hidden;

                searchValueMedicalStaff = searcStaffUsernameTextBox.Text;
            }

            else if (staffHistorySelectedValueFilter == "Gender")
            {
                staff_address_dependent.Visibility = Visibility.Visible;
                staff_birthdate_dependent.Visibility = Visibility.Hidden;
                staff_number_dependent.Visibility = Visibility.Hidden;

                searchValueMedicalStaff = staffHistorySelectedValueGender;
            }

            else if (staffHistorySelectedValueFilter == "Birthdate")
            {
                staff_address_dependent.Visibility = Visibility.Hidden;
                staff_birthdate_dependent.Visibility = Visibility.Visible;
                staff_number_dependent.Visibility = Visibility.Hidden;

                searchValueMedicalStaff = searchBirthDateStaffHistory.Text;
            }

            else if (staffHistorySelectedValueFilter == "Address")
            {
                staff_address_dependent.Visibility = Visibility.Visible;
                staff_birthdate_dependent.Visibility = Visibility.Hidden;
                staff_number_dependent.Visibility = Visibility.Hidden;

                searchValueMedicalStaff = searcStaffUsernameTextBox.Text;
            }

            else if (staffHistorySelectedValueFilter == "Number")
            {
                staff_address_dependent.Visibility = Visibility.Hidden;
                staff_birthdate_dependent.Visibility = Visibility.Hidden;
                staff_number_dependent.Visibility = Visibility.Visible;

                searchValueMedicalStaff = searchStaffHistoryTextBoxNumber.Text;
            }

            else if (staffHistorySelectedValueFilter == "Date Registered")
            {
                staff_address_dependent.Visibility = Visibility.Visible;
                staff_birthdate_dependent.Visibility = Visibility.Hidden;
                staff_number_dependent.Visibility = Visibility.Hidden;

                searchValueMedicalStaff = searchBirthDateStaffHistory.Text;
            }

            else if (staffHistorySelectedValueFilter == "Date Deleted")
            {
                staff_address_dependent.Visibility = Visibility.Visible;
                staff_birthdate_dependent.Visibility = Visibility.Hidden;
                staff_number_dependent.Visibility = Visibility.Hidden;

                searchValueMedicalStaff = searchBirthDateStaffHistory.Text;
            }

            else
            {
                staff_address_dependent.Visibility = Visibility.Visible;
                staff_birthdate_dependent.Visibility = Visibility.Hidden;
                staff_number_dependent.Visibility = Visibility.Hidden;

                searchValueMedicalStaff = "";
            }

            var url = "http://192.168.254.114:8080/smartcare/searchStaffHistory.php";

            var client = new HttpClient();

            var data = new Dictionary<string, string>
            {
                {"selectedValue", staffHistorySelectedValueFilter},
                {"searchValue", searchValueMedicalStaff}
            };

            var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
            var content = await httpResponseMessage.Content.ReadAsStringAsync();

            string patientSearchHistoryResult = content.ToString();
            historyGridTableStaff.Items.Clear();

            var historyPatient = patientSearchHistoryResult;

            var historyStaff = historyPatient;

            for (; ; )
            {
                if (historyStaff == "" || historyStaff == " ")
                {
                    break;
                }

                else
                {
                    string historyStaffID = historyStaff.Substring(0, historyStaff.IndexOf("`"));
                    int historyStaffID_int = Convert.ToInt32(historyStaffID);// returns int
                    int historyStaffID_length = historyStaffID.Length;
                    int historyStaffID_length1 = historyStaffID_length + 1;
                    string finalNewList = historyStaff.Remove(0, historyStaffID_length1);

                    historyStaff = finalNewList;

                    string historyStaffUsername = historyStaff.Substring(0, historyStaff.IndexOf("`"));
                    int historyStaffUsername_length = historyStaffUsername.Length;
                    int historyStaffUsername_length1 = historyStaffUsername_length + 1;
                    string finalNewList1a = historyStaff.Remove(0, historyStaffUsername_length1);

                    historyStaff = finalNewList1a;

                    string historyStaffName = historyStaff.Substring(0, historyStaff.IndexOf("`"));
                    int historyStaffName_length = historyStaffName.Length;
                    int historyStaffName_length1 = historyStaffName_length + 1;
                    string finalNewList1 = historyStaff.Remove(0, historyStaffName_length1);

                    historyStaff = finalNewList1;

                    string historyStaffGender = historyStaff.Substring(0, historyStaff.IndexOf("`"));
                    int historyStaffGender_length = historyStaffGender.Length;
                    int historyStaffGender_length1 = historyStaffGender_length + 1;
                    string finalNewList2 = historyStaff.Remove(0, historyStaffGender_length1);

                    historyStaff = finalNewList2;

                    string historyStaffDependent = historyStaff.Substring(0, historyStaff.IndexOf("`"));
                    int historyStaffDependent_length = historyStaffDependent.Length;
                    int historyStaffDependent_length1 = historyStaffDependent_length + 1;
                    string finalNewList3 = historyStaff.Remove(0, historyStaffDependent_length1);

                    historyStaff = finalNewList3;

                    string historyStaffRegistered = historyStaff.Substring(0, historyStaff.IndexOf("`"));
                    int historyStaffRegistered_length = historyStaffRegistered.Length;
                    int historyStaffRegistered_length1 = historyStaffRegistered_length + 1;
                    string finalNewList3a = historyStaff.Remove(0, historyStaffRegistered_length1);

                    historyStaff = finalNewList3a;

                    string historyStaffDeleted = historyStaff.Substring(0, historyStaff.IndexOf("`"));
                    int historyStaffDeleted_length = historyStaffDeleted.Length;
                    int historyStaffDeleted_length1 = historyStaffDeleted_length + 1;
                    string finalNewList3b = historyStaff.Remove(0, historyStaffDeleted_length1);

                    historyStaff = finalNewList3b;

                    historyGridTableStaff.Items.Add(new historyStaffData { staff_id = historyStaffID_int, username = historyStaffUsername, staff_name = historyStaffName, gender = historyStaffGender, dependent = historyStaffDependent, date_registered = historyStaffRegistered, date_deleted = historyStaffDeleted });
                }
            }

        }

        private void filterStaffHistoryGender_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            staffHistorySelectedValueGender = ((System.Windows.Controls.ComboBoxItem)filterStaffHistoryGender.SelectedItem).Content as string;
        }

        private void btnViewStaff_Click(object sender, RoutedEventArgs e)
        {
            var selectedIndexStaffHistoryGrid = historyGridTableStaff.SelectedIndex;
            TextBlock selectedHistoryStaffIndexValue = historyGridTableStaff.Columns[0].GetCellContent(historyGridTableStaff.Items[selectedIndexStaffHistoryGrid]) as TextBlock;
            staffID1 = selectedHistoryStaffIndexValue.Text;
            historyOrNot = "yes";

            /* var url = "http://192.168.254.114:8080/smartcare/update_current_clicked_user.php";

            var client = new HttpClient();

            var data = new Dictionary<string, string>
{
                {"user_id", historyStaffID}
            };

            var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
            var content = await httpResponseMessage.Content.ReadAsStringAsync(); */


            foreach (Window w in Application.Current.Windows)
            {
                if (w is staffInformation)
                {
                    w.Close();

                    clicked = 0;
                }
            }

            if (clicked == 0)
            {
                staffInformation newwindow = new staffInformation();
                newwindow.Show();
            }

        }

        private async void searchButtonHistoryLog_Click(object sender, RoutedEventArgs e)
        {
            string searchValueHistoryLog;

            if (selectedHistoryLogFilter == "Activity")
            {
                searchValueHistoryLog = historyLogSelectedValueActivity;

                searchButtonHistoryLog.Margin = new Thickness(657, 35, -64, 0);
            }

            else if (selectedHistoryLogFilter == "Medical Staff Name")
            {
                searchValueHistoryLog = searchPatientNameActivity.Text;

                searchButtonHistoryLog.Margin = new Thickness(593, 35, 0, 0);
            }

            else if (selectedHistoryLogFilter == "Activity Date")
            {
                searchValueHistoryLog = searchDateHistoryLog.Text;

                searchButtonHistoryLog.Margin = new Thickness(593, 35, 0, 0);
            }

            else if (selectedHistoryLogFilter == "Activity Time")
            {
                searchValueHistoryLog = historyLogSelectedValueTime;

                searchButtonHistoryLog.Margin = new Thickness(593, 35, 0, 0);
            }

            else
            {
                searchValueHistoryLog = "";
            }

            var url = "http://192.168.254.114:8080/smartcare/searchHistoryLog.php";

            var client = new HttpClient();

            var data = new Dictionary<string, string>
            {
                {"selectedValue", selectedHistoryLogFilter},
                {"searchValue", searchValueHistoryLog}
            };


            var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
            var content = await httpResponseMessage.Content.ReadAsStringAsync();

            string patientSearchHistoryResult = content.ToString();
            historyGridTable.Items.Clear();

            var historyPatient = patientSearchHistoryResult;

            var history = historyPatient;

            for (; ; )
            {
                if (history == "" || history == " ")
                {
                    break;
                }

                else
                {
                    string historyID = history.Substring(0, history.IndexOf("`"));
                    int historyID_int = Convert.ToInt32(historyID);// returns int
                    int historyID_length = historyID.Length;
                    int historyID_length1 = historyID_length + 1;
                    string finalNewList = history.Remove(0, historyID_length1);

                    history = finalNewList;

                    string history_staff_name = history.Substring(0, history.IndexOf("`"));
                    int history_staff_name_length = history_staff_name.Length;
                    int history_staff_name_length1 = history_staff_name_length + 1;
                    string finalNewList1 = history.Remove(0, history_staff_name_length1);

                    history = finalNewList1;

                    string historyDateStamp = history.Substring(0, history.IndexOf("`"));
                    int historyDateStamp_length = historyDateStamp.Length;
                    int historyDateStamp_length1 = historyDateStamp_length + 1;
                    string finalNewList2 = history.Remove(0, historyDateStamp_length1);

                    history = finalNewList2;

                    string historyTimeStamp = history.Substring(0, history.IndexOf("`"));
                    int historyTimeStamp_length = historyTimeStamp.Length;
                    int historyTimeStamp_length1 = historyTimeStamp_length + 1;
                    string finalNewList3 = history.Remove(0, historyTimeStamp_length1);

                    history = finalNewList3;

                    string historyActivity = history.Substring(0, history.IndexOf("`"));
                    int historyActivity_length = historyActivity.Length;
                    int historyActivity_length1 = historyActivity_length + 1;
                    string finalNewList4 = history.Remove(0, historyActivity_length1);

                    history = finalNewList4;

                    historyGridTable.Items.Add(new historyLogData { history_id = historyID_int, activity = historyActivity, staff_name = history_staff_name, dateStamp = historyDateStamp, timeStamp = historyTimeStamp });
                }
            }
        }

        private void filterActivity_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            historyLogSelectedValueActivity = ((System.Windows.Controls.ComboBoxItem)filterActivity.SelectedItem).Content as string;
        }

        private void timeFilterHistoryLog_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            historyLogSelectedValueTime = ((System.Windows.Controls.ComboBoxItem)timeFilterHistoryLog.SelectedItem).Content as string;
        }

        private void btnViewHealthLog_Click(object sender, RoutedEventArgs e)
        {
            var selectedIndexPatient1 = historyGridTableHealth.SelectedIndex;
            TextBlock xPatient1 = historyGridTableHealth.Columns[0].GetCellContent(historyGridTableHealth.Items[selectedIndexPatient1]) as TextBlock;
            patientHealth1 = xPatient1.Text;
            historyOrNot = "yes";

            /*var url = "http://192.168.254.114:8080/smartcare/update_current_clicked_patient.php";

            var client = new HttpClient();

            var data = new Dictionary<string, string>{
                {"patient_id", patientID1}
            };

            var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
            var content = await httpResponseMessage.Content.ReadAsStringAsync();*/

            foreach (Window w in Application.Current.Windows)
            {
                if (w is patientHealthInformation)
                {
                    w.Close();

                    clicked = 0;
                }
            }

            if (clicked == 0)
            {
                patientHealthInformation newwindowPatienHealthtInfo = new patientHealthInformation();
                newwindowPatienHealthtInfo.Show();
            }
        }

        private async void searchButtonHealthHistory_Click(object sender, RoutedEventArgs e)
        {
            string searchHealthValue;

            if (selectedHealthHistoryFilter == "Patient Name")
            {
                searchHealthValue = searchPatientNameHealth.Text;
            }

            else if (selectedHealthHistoryFilter == "Date Encoded")
            {
                searchHealthValue = searchDateHealth.Text;
            }

            else if (selectedHealthHistoryFilter == "Time Encoded")
            {
                searchHealthValue = historyHealthLogSelectedValueTime;
            }

            else
            {
                searchHealthValue = "";
            }

            var url = "http://192.168.254.114:8080/smartcare/searchHealthHistory.php";

            var client = new HttpClient();

            var data = new Dictionary<string, string>
            {
                {"selectedValue", selectedHealthHistoryFilter},
                {"searchValue", searchHealthValue}
            };


            var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
            var content = await httpResponseMessage.Content.ReadAsStringAsync();

            string patientSearchHistoryResult = content.ToString();
            historyGridTableHealth.Items.Clear();

            var historyPatient = patientSearchHistoryResult;

            var hsitoryHealth = historyPatient;

            for (; ; )
            {
                if (hsitoryHealth == "" || hsitoryHealth == " ")
                {
                    break;
                }

                else
                {
                    string historyHealthID = hsitoryHealth.Substring(0, hsitoryHealth.IndexOf("`"));
                    int historyHealthID_int = Convert.ToInt32(historyHealthID);// returns int
                    int historyHealthID_length = historyHealthID.Length;
                    int historyHealthID_length1 = historyHealthID_length + 1;
                    string finalNewList = hsitoryHealth.Remove(0, historyHealthID_length1);

                    hsitoryHealth = finalNewList;

                    string historyHealthPatientID = hsitoryHealth.Substring(0, hsitoryHealth.IndexOf("`"));
                    int historyHealthPatientID_length = historyHealthPatientID.Length;
                    int historyHealthPatientID_length1 = historyHealthPatientID_length + 1;
                    string finalNewList1 = hsitoryHealth.Remove(0, historyHealthPatientID_length1);

                    hsitoryHealth = finalNewList1;

                    string historyHealthDate = hsitoryHealth.Substring(0, hsitoryHealth.IndexOf("`"));
                    int historyHealthDate_length = historyHealthDate.Length;
                    int historyHealthDate_length1 = historyHealthDate_length + 1;
                    string finalNewList2 = hsitoryHealth.Remove(0, historyHealthDate_length1);

                    hsitoryHealth = finalNewList2;

                    string historyHealthTime = hsitoryHealth.Substring(0, hsitoryHealth.IndexOf("`"));
                    int historyHealthTime_length = historyHealthTime.Length;
                    int historyHealthTime_length1 = historyHealthTime_length + 1;
                    string finalNewList3 = hsitoryHealth.Remove(0, historyHealthTime_length1);

                    hsitoryHealth = finalNewList3;

                    historyGridTableHealth.Items.Add(new historyHealthData { health_id = historyHealthID_int, patient_name = historyHealthPatientID, date = historyHealthDate, time = historyHealthTime });
                }
            }
        }

        private void timeFilter_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            historyHealthLogSelectedValueTime = ((System.Windows.Controls.ComboBoxItem)timeFilter.SelectedItem).Content as string;
        }


        private void ComboBox_SelectionChanged_1(object sender, SelectionChangedEventArgs e)
        {
            roomSelectedValue = ((System.Windows.Controls.ComboBoxItem)comboBoxRoom.SelectedItem).Content as string;

            if (roomSelectedValue == "Room Number")
            {
                roomNumber.Text = "";
                roomUser.Text = "";
                roomStatus.Text = "Available";

                roomNumber.Visibility = Visibility.Visible;
                roomUser.Visibility = Visibility.Hidden;
                roomStatus.Visibility = Visibility.Hidden;
            }

            else if (roomSelectedValue == "Status")
            {
                roomNumber.Text = "";
                roomUser.Text = "";
                roomStatus.Text = "Available";

                roomNumber.Visibility = Visibility.Hidden;
                roomUser.Visibility = Visibility.Hidden;
                roomStatus.Visibility = Visibility.Visible;
            }

            else if (roomSelectedValue == "Current User Name")
            {
                roomNumber.Text = "";
                roomUser.Text = "";
                roomStatus.Text = "Available";

                roomNumber.Visibility = Visibility.Hidden;
                roomUser.Visibility = Visibility.Visible;
                roomStatus.Visibility = Visibility.Hidden;
            }
        }

        private async void searchButtonRoom_Click(object sender, RoutedEventArgs e)
        {
            string searchValueRoom;

            if (roomSelectedValue == "Room Number")
            {
                searchValueRoom = roomNumber.Text;
            }

            else if (roomSelectedValue == "Status")
            {
                searchValueRoom = roomSelectedStatus;
            }

            else if (roomSelectedValue == "Current User Name")
            {
                searchValueRoom = roomUser.Text;
            }

            else
            {
                searchValueRoom = "";
            }

            var url = "http://192.168.254.114:8080/smartcare/searchRoom.php";

            var client = new HttpClient();

            var data = new Dictionary<string, string>
            {
                {"selectedValue", roomSelectedValue},
                {"searchValue", searchValueRoom}
            };


            var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
            var content = await httpResponseMessage.Content.ReadAsStringAsync();

            string patientSearchHistoryResult = content.ToString();
            roomGrid.Items.Clear();

            var historyPatient = patientSearchHistoryResult;

            var room = historyPatient;

            for (; ; )
            {
                if (room == "" || room == " ")
                {
                    break;
                }

                else
                {

                    string roomNumber = room.Substring(0, room.IndexOf("`"));
                    int roomNumber_int = Convert.ToInt32(roomNumber);// returns int
                    int roomNumber_length = roomNumber.Length;
                    int roomNumber_length1 = roomNumber_length + 1;
                    string finalNewList = room.Remove(0, roomNumber_length1);

                    room = finalNewList;

                    string roomStatus = room.Substring(0, room.IndexOf("`"));
                    int roomStatus_length = roomStatus.Length;
                    int roomStatus_length1 = roomStatus_length + 1;
                    string finalNewList1 = room.Remove(0, roomStatus_length1);

                    room = finalNewList1;

                    string currentUser = room.Substring(0, room.IndexOf("`"));
                    int currentUser_length = currentUser.Length;
                    int currentUser_length1 = currentUser_length + 1;
                    string finalNewList2 = room.Remove(0, currentUser_length1);

                    room = finalNewList2;

                    roomGrid.Items.Add(new roomData { room_number = roomNumber_int, room_status = roomStatus, current_user = currentUser });
                }
            }
        }

        private void roomStatus_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            roomSelectedStatus = ((System.Windows.Controls.ComboBoxItem)roomStatus.SelectedItem).Content as string;
        }

        public void getAllRoom()
        {
            HttpClient client = new HttpClient();
            client.BaseAddress = new Uri("http://192.168.254.114:8080/smartcare/");
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));


            HttpResponseMessage response = client.GetAsync("getRooms.php").Result;
            if (response.IsSuccessStatusCode)
            {
                var room = response.Content.ReadAsStringAsync().Result;

                for (; ; )
                {
                    if (room == "" || room == " ")
                    {
                        break;
                    }

                    else
                    {
                        string roomNumber = room.Substring(0, room.IndexOf("`"));
                        int roomNumber_int = Convert.ToInt32(roomNumber);// returns int
                        int roomNumber_length = roomNumber.Length;
                        int roomNumber_length1 = roomNumber_length + 1;
                        string finalNewList = room.Remove(0, roomNumber_length1);

                        room = finalNewList;

                        string roomStatus = room.Substring(0, room.IndexOf("`"));
                        int roomStatus_length = roomStatus.Length;
                        int roomStatus_length1 = roomStatus_length + 1;
                        string finalNewList1 = room.Remove(0, roomStatus_length1);

                        room = finalNewList1;

                        string currentUser = room.Substring(0, room.IndexOf("`"));
                        int currentUser_length = currentUser.Length;
                        int currentUser_length1 = currentUser_length + 1;
                        string finalNewList2 = room.Remove(0, currentUser_length1);

                        room = finalNewList2;

                        roomGrid.Items.Add(new roomData { room_number = roomNumber_int, room_status = roomStatus, current_user = currentUser });
                    }
                }
            }
            else
            {
                MessageBox.Show("Error Code" + response.StatusCode + " : Message - " + response.ReasonPhrase);
            }
        }

        private void editHospitalInfo_Click(object sender, RoutedEventArgs e)
        {
            editHospitalInfo.Margin = new Thickness(146, 273, 0, 0);

            edited.Visibility = Visibility.Visible;
            notEdited.Visibility = Visibility.Hidden;
            editedDate.Visibility = Visibility.Visible;
            notEditedDate.Visibility = Visibility.Hidden;

            hospitalNameTextBox.Visibility = Visibility.Visible;
            hospitalNameText.Visibility = Visibility.Hidden;
            addressTextBox.Visibility = Visibility.Visible;
            hospitalAddressText.Visibility = Visibility.Hidden;
            hospitalEmailTextBox.Visibility = Visibility.Visible;
            hospitalEmailText.Visibility = Visibility.Hidden;
            hospitalDateTextBox.Visibility = Visibility.Visible;
            dateText.Visibility = Visibility.Hidden;

            saveInfo.Visibility = Visibility.Visible;
            cancel.Visibility = Visibility.Visible;
            editHospitalInfo.Visibility = Visibility.Hidden;
        }

        private void cancel_Click(object sender, RoutedEventArgs e)
        {
            editHospitalInfo.Margin = new Thickness(93, 273, 0, 0);

            edited.Visibility = Visibility.Hidden;
            notEdited.Visibility = Visibility.Visible;
            editedDate.Visibility = Visibility.Hidden;
            notEditedDate.Visibility = Visibility.Visible;

            hospitalNameTextBox.Visibility = Visibility.Hidden;
            hospitalNameText.Visibility = Visibility.Visible;
            addressTextBox.Visibility = Visibility.Hidden;
            hospitalAddressText.Visibility = Visibility.Visible;
            hospitalEmailTextBox.Visibility = Visibility.Hidden;
            hospitalEmailText.Visibility = Visibility.Visible;
            hospitalDateTextBox.Visibility = Visibility.Hidden;
            dateText.Visibility = Visibility.Visible;

            saveInfo.Visibility = Visibility.Hidden;
            cancel.Visibility = Visibility.Hidden;
            editHospitalInfo.Visibility = Visibility.Visible;
        }

        private async void saveInfo_Click(object sender, RoutedEventArgs e)
        {
            if(hospitalNameTextBox.Text == "")
            {
                MessageBox.Show("Hospital name is required!");
                hospitalNameTextBox.Focus();
            }

            else
            {
                var DialogResult = MessageBox.Show("Are you sure you want to update hospital information?", "Update Hostpital Information", MessageBoxButton.YesNo, MessageBoxImage.Question, MessageBoxResult.No);
                if (DialogResult == MessageBoxResult.Yes)
                {
                    var url = "http://192.168.254.114:8080/smartcare/updateHospitalInformation.php";

                    var client = new HttpClient();

                    var data = new Dictionary<string, string>
                {
                    {"hospital_name", hospitalNameTextBox.Text},
                    {"hospital_address", addressTextBox.Text},
                    {"hospital_email", hospitalEmailTextBox.Text},
                    {"hospital_date_acquired", hospitalDateTextBox.Text}
                };


                    var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
                    var content = await httpResponseMessage.Content.ReadAsStringAsync();

                    if (content != "")
                    {
                        var hospitalInfo = content;
                        for (; ; )
                        {
                            if (hospitalInfo == "" || hospitalInfo == " ")
                            {
                                break;
                            }

                            else
                            {
                                string hospitalName = hospitalInfo.Substring(0, hospitalInfo.IndexOf("`"));
                                int hospitalName_length = hospitalName.Length;
                                int hospitalName_length1 = hospitalName_length + 1;
                                string finalNewList = hospitalInfo.Remove(0, hospitalName_length1);

                                if (hospitalName.Length > 35)
                                {
                                    string formattedHospitalName = hospitalName.Substring(0, 34);
                                    hospitalNameText.Text = formattedHospitalName + "...";
                                }

                                else
                                {
                                    hospitalNameText.Text = hospitalName;
                                }
                                hospitalNameTextBox.Text = hospitalName;

                                hospitalInfo = finalNewList;

                                string hospitalAddress = hospitalInfo.Substring(0, hospitalInfo.IndexOf("`"));
                                int hospitalAddress_length = hospitalAddress.Length;
                                int hospitalAddress_length1 = hospitalAddress_length + 1;
                                string finalNewList1 = hospitalInfo.Remove(0, hospitalAddress_length1);
                                if (hospitalAddress.Length > 35)
                                {
                                    string formattedHospitalAddress = hospitalAddress.Substring(0, 34);
                                    hospitalAddressText.Text = formattedHospitalAddress + "...";
                                }

                                else
                                {
                                    hospitalAddressText.Text = hospitalAddress;
                                }

                                addressTextBox.Text = hospitalAddress;

                                hospitalInfo = finalNewList1;

                                string hospitalEmail = hospitalInfo.Substring(0, hospitalInfo.IndexOf("`"));
                                int hospitalEmail_length = hospitalEmail.Length;
                                int hospitalEmail_length1 = hospitalEmail_length + 1;
                                string finalNewList2 = hospitalInfo.Remove(0, hospitalEmail_length1);
                                if (hospitalEmail.Length > 35)
                                {
                                    string formattedHospitalEmail = hospitalEmail.Substring(0, 34);
                                    hospitalEmailText.Text = formattedHospitalEmail + "...";
                                }

                                else
                                {
                                    hospitalEmailText.Text = hospitalEmail;
                                }

                                hospitalEmailTextBox.Text = hospitalEmail;

                                hospitalInfo = finalNewList2;
                                dateText.Text = hospitalInfo;
                                hospitalDateTextBox.Text = hospitalInfo;

                                hospitalInfo = "";

                            }
                        }

                        MessageBox.Show("Hospital Information updated successfully");

                        editHospitalInfo.Margin = new Thickness(93, 273, 0, 0);

                        edited.Visibility = Visibility.Hidden;
                        notEdited.Visibility = Visibility.Visible;
                        editedDate.Visibility = Visibility.Hidden;
                        notEditedDate.Visibility = Visibility.Visible;

                        hospitalNameTextBox.Visibility = Visibility.Hidden;
                        hospitalNameText.Visibility = Visibility.Visible;
                        addressTextBox.Visibility = Visibility.Hidden;
                        hospitalAddressText.Visibility = Visibility.Visible;
                        hospitalEmailTextBox.Visibility = Visibility.Hidden;
                        hospitalEmailText.Visibility = Visibility.Visible;
                        hospitalDateTextBox.Visibility = Visibility.Hidden;
                        dateText.Visibility = Visibility.Visible;

                        saveInfo.Visibility = Visibility.Hidden;
                        cancel.Visibility = Visibility.Hidden;
                        editHospitalInfo.Visibility = Visibility.Visible;
                    }

                    else
                    {
                        MessageBox.Show("Failure to update hospital information", "Update Error", MessageBoxButton.OK, MessageBoxImage.Error);
                    }

                }
            }
            

        }

        private void getHospitalInformation()
        {
            HttpClient client = new HttpClient();
            client.BaseAddress = new Uri("http://192.168.254.114:8080/smartcare/");
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));


            HttpResponseMessage response = client.GetAsync("getHospitalInformation.php").Result;
            if (response.IsSuccessStatusCode)
            {
                var hospitalInfo = response.Content.ReadAsStringAsync().Result;

                for (; ; )
                {
                    if (hospitalInfo == "" || hospitalInfo == " ")
                    {
                        break;
                    }

                    else
                    {
                        string hospitalName = hospitalInfo.Substring(0, hospitalInfo.IndexOf("`"));
                        int hospitalName_length = hospitalName.Length;
                        int hospitalName_length1 = hospitalName_length + 1;
                        string finalNewList = hospitalInfo.Remove(0, hospitalName_length1);

                        if (hospitalName.Length > 35)
                        {
                            string formattedHospitalName = hospitalName.Substring(0, 34);
                            hospitalNameText.Text = formattedHospitalName + "...";
                        }

                        else
                        {
                            hospitalNameText.Text = hospitalName;
                        }
                        hospitalNameTextBox.Text = hospitalName;

                        hospitalInfo = finalNewList;

                        string hospitalAddress = hospitalInfo.Substring(0, hospitalInfo.IndexOf("`"));
                        int hospitalAddress_length = hospitalAddress.Length;
                        int hospitalAddress_length1 = hospitalAddress_length + 1;
                        string finalNewList1 = hospitalInfo.Remove(0, hospitalAddress_length1);
                        if (hospitalAddress.Length > 35)
                        {
                            string formattedHospitalAddress = hospitalAddress.Substring(0, 34);
                            hospitalAddressText.Text = formattedHospitalAddress + "...";
                        }

                        else
                        {
                            hospitalAddressText.Text = hospitalAddress;
                        }

                        addressTextBox.Text = hospitalAddress;

                        hospitalInfo = finalNewList1;

                        string hospitalEmail = hospitalInfo.Substring(0, hospitalInfo.IndexOf("`"));
                        int hospitalEmail_length = hospitalEmail.Length;
                        int hospitalEmail_length1 = hospitalEmail_length + 1;
                        string finalNewList2 = hospitalInfo.Remove(0, hospitalEmail_length1);
                        if (hospitalEmail.Length > 35)
                        {
                            string formattedHospitalEmail = hospitalEmail.Substring(0, 34);
                            hospitalEmailText.Text = formattedHospitalEmail + "...";
                        }

                        else
                        {
                            hospitalEmailText.Text = hospitalEmail;
                        }

                        hospitalEmailTextBox.Text = hospitalEmail;

                        hospitalInfo = finalNewList2;
                        dateText.Text = hospitalInfo;
                        hospitalDateTextBox.Text = hospitalInfo;

                        hospitalInfo = "";

                    }
                }
            }
            else
            {
                MessageBox.Show("Error Code" + response.StatusCode + " : Message - " + response.ReasonPhrase);
            }
        }

        private void btnViewHealthPatient_Click(object sender, RoutedEventArgs e){
            var selectedIndexPatient = patientGrid.SelectedIndex;
            TextBlock xPatient = patientGrid.Columns[0].GetCellContent(patientGrid.Items[selectedIndexPatient]) as TextBlock;
            patientHealth1 = xPatient.Text;
            historyOrNot = "no";
            //here1
            
            /*var url = "http://192.168.254.114:8080/smartcare/update_current_clicked_patient.php";

            var client = new HttpClient();

            var data = new Dictionary<string, string>{
                {"patient_id", patientID}
            };

            var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
            var content = await httpResponseMessage.Content.ReadAsStringAsync();*/

            foreach (Window w in Application.Current.Windows)
            {
                if (w is patientHealthInformation)
                {
                    w.Close();

                    clicked = 0;
                }
            }

            if (clicked == 0)
            {
                patientHealthInformation newwindowPatienHealthtInfo = new patientHealthInformation();
                newwindowPatienHealthtInfo.Show();
            }
        }

        private void addRoom_Click(object sender, RoutedEventArgs e)
        {
            foreach (Window w in Application.Current.Windows)
            {
                if (w is registerRoom)
                {
                    w.Close();

                    clicked = 0;
                }
            }

            if (clicked == 0)
            {
                registerRoom newwindowRegisterRoom = new registerRoom();
                newwindowRegisterRoom.Show();
            }
        }

        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            if(selectedCategory == "History Log")
            {
                log_id.Visibility = Visibility.Hidden;
                historyGridTable.SelectAllCells();
                historyGridTable.ClipboardCopyMode = DataGridClipboardCopyMode.IncludeHeader;
                ApplicationCommands.Copy.Execute(null, historyGridTable);
                historyGridTable.UnselectAllCells();
            }

            else if(selectedCategory == "Patient Health Logs")
            {
                healthID.Visibility = Visibility.Hidden;

                historyGridTableHealth.SelectAllCells();
                historyGridTableHealth.ClipboardCopyMode = DataGridClipboardCopyMode.IncludeHeader;
                ApplicationCommands.Copy.Execute(null, historyGridTableHealth);
                historyGridTableHealth.UnselectAllCells();
            }

            else if (selectedCategory == "Patient History")
            {
                patientID.Visibility = Visibility.Hidden;

                historyGridTablePatient.SelectAllCells();
                historyGridTablePatient.ClipboardCopyMode = DataGridClipboardCopyMode.IncludeHeader;
                ApplicationCommands.Copy.Execute(null, historyGridTablePatient);
                historyGridTablePatient.UnselectAllCells();
            }

            else if (selectedCategory == "Medical Staff Histor")
            {
                staff_id.Visibility = Visibility.Hidden;
                historyGridTableStaff.SelectAllCells();
                historyGridTableStaff.ClipboardCopyMode = DataGridClipboardCopyMode.IncludeHeader;
                ApplicationCommands.Copy.Execute(null, historyGridTableStaff);
                historyGridTableStaff.UnselectAllCells();
            }

            String result = (string)Clipboard.GetData(DataFormats.CommaSeparatedValue);
            try
            {

                Microsoft.Win32.SaveFileDialog dlg = new Microsoft.Win32.SaveFileDialog();
                dlg.DefaultExt = ".csv"; // Default file extension
                dlg.Filter = "Microsoft Excel (.csv)|*.csv"; // Filter files by extension

                // Show save file dialog box
                Nullable<bool> result1 = dlg.ShowDialog();

                // Process save file dialog box results
                if (result1 == true)
                {
                    string path = dlg.FileName;
                    StreamWriter sw = new StreamWriter(path+".csv");
                    sw.WriteLine(result);
                    sw.Close();
                    MessageBox.Show("Data Exported Successfully");
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }

        }

        private void getHospitalName()
        {
            HttpClient client = new HttpClient();
            client.BaseAddress = new Uri("http://192.168.254.114:8080/smartcare/");
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));


            HttpResponseMessage response = client.GetAsync("getHospitalName.php").Result;
            if (response.IsSuccessStatusCode)
            {
                var hospitalInfo = response.Content.ReadAsStringAsync().Result;
                hospitalName.Content = hospitalInfo;
            }
        }

        private void indexButton_Click(object sender, RoutedEventArgs e)
        {
            getHospitalName();

            homeGrid.Visibility = Visibility.Hidden;
            deviceGrid.Visibility = Visibility.Hidden;
            historyGrid.Visibility = Visibility.Hidden;
            contactsGrid.Visibility = Visibility.Hidden;
            aboutGrid.Visibility = Visibility.Hidden;
            indexGrid.Visibility = Visibility.Visible;
        }

        private void historyGridTable_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {

        }

        private async void btnDeleteRoom_Click(object sender, RoutedEventArgs e)
        {
            var DialogResult = MessageBox.Show("Are you sure you want to delete this room?", "Delete Room", MessageBoxButton.YesNo, MessageBoxImage.Question, MessageBoxResult.No);

            if (DialogResult == MessageBoxResult.Yes)
            {
                var selectedIndex2 = roomGrid.SelectedIndex;
                TextBlock x2 = roomGrid.Columns[0].GetCellContent(roomGrid.Items[selectedIndex2]) as TextBlock;
                string roomNumber = x2.Text;

                var url = "http://192.168.254.114:8080/smartcare/removeRoom.php";

                var client = new HttpClient();

                var data = new Dictionary<string, string>{
                {"room_number", roomNumber}
                };

                var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
                var content = await httpResponseMessage.Content.ReadAsStringAsync();

                if (content == "used")
                {
                    MessageBox.Show("Room cannot be delete, in used", "Delete Error", MessageBoxButton.OK, MessageBoxImage.Error);
                }

                else if (content == "success")
                {
                    MessageBox.Show("Room successfully deleted");
                    roomGrid.Items.Clear();
                    getAllRoom();
                }

                else
                {
                    MessageBox.Show("Delete operation error", "Delete Error", MessageBoxButton.OK, MessageBoxImage.Error);
                }

            }
        }
        

    }
}