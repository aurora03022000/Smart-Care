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
    /// Interaction logic for patientinformation.xaml
    /// </summary>
    public partial class patientinformation : Window
    {
        string selectedGender;

        string currentRoom;
        string patientID;
        string historyOrNot1;

        public patientinformation()
        {
            InitializeComponent();

            patientID = Home.instance.patientID1;
            historyOrNot1 = Home.instance.historyOrNot;

            if(historyOrNot1 == "yes")
            {
                editPatientInfo.Visibility = Visibility.Hidden;
            }
    
            getPatientInformation();

        }

        private async void getPatientInformation()
        {
            var url = "http://192.168.43.66:8080/smartcare/getPatientInformationAdmin.php";

            var client = new HttpClient();

            var data = new Dictionary<string, string>
{
                {"patient_id", patientID}
            };
            var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
            var content = await httpResponseMessage.Content.ReadAsStringAsync();

            if (content != "" || content != " " || content != "failure")
            {
                var patientInfo = content;

                for (; ; )
                {
                    if (patientInfo == "" || patientInfo == " ")
                    {
                        break;
                    }

                    else
                    {
                        string patient_id = patientInfo.Substring(0, patientInfo.IndexOf("`"));
                        int patient_id_length = patient_id.Length;
                        int patient_id_length1 = patient_id_length + 1;
                        string finalNewLista = patientInfo.Remove(0, patient_id_length1);
                        id.Text = patient_id;

                        patientInfo = finalNewLista;

                        string patient_name = patientInfo.Substring(0, patientInfo.IndexOf("`"));
                        int patient_name_length = patient_name.Length;
                        int patient_name_length1 = patient_name_length + 1;
                        string finalNewList = patientInfo.Remove(0, patient_name_length1);
                        textName.Text = patient_name;
                        name.Text = patient_name;

                        patientInfo = finalNewList;

                        string patient_gender = patientInfo.Substring(0, patientInfo.IndexOf("`"));
                        int patient_gender_length = patient_gender.Length;
                        int patient_gender_length1 = patient_gender_length + 1;
                        string finalNewList1a = patientInfo.Remove(0, patient_gender_length1);
                        textGender.Text = patient_gender;
                        gender.Text = patient_gender;

                        patientInfo = finalNewList1a;

                        string patient_birthdate = patientInfo.Substring(0, patientInfo.IndexOf("`"));
                        int patient_birthdate_length = patient_birthdate.Length;
                        int patient_birthdate_length1 = patient_birthdate_length + 1;
                        string finalNewList1b = patientInfo.Remove(0, patient_birthdate_length1);
                        textBdate.Text = patient_birthdate;
                        bdate.Text = patient_birthdate;

                        patientInfo = finalNewList1b;

                        string patient_address = patientInfo.Substring(0, patientInfo.IndexOf("`"));
                        int patient_address_length = patient_address.Length;
                        int patient_address_length1 = patient_address_length + 1;
                        string finalNewList4 = patientInfo.Remove(0, patient_address_length1);

                        if (patient_address_length >= 37)
                        {
                            string formattedAddress = patient_address.Substring(0, 37);
                            textAddress.Text = formattedAddress + "...";
                        }

                        else
                        {
                            textAddress.Text = patient_address;
                        }

                        address.Text = patient_address;


                        patientInfo = finalNewList4;

                        string patient_number = patientInfo.Substring(0, patientInfo.IndexOf("`"));
                        int patient_number_length = patient_number.Length;
                        int patient_number_length1 = patient_number_length + 1;
                        string finalNewList1c = patientInfo.Remove(0, patient_number_length1);
                        textNumber.Text = patient_number;
                        number.Text = patient_number;

                        patientInfo = finalNewList1c;

                        string patient_disease = patientInfo.Substring(0, patientInfo.IndexOf("`"));
                        int patient_disease_length = patient_disease.Length;
                        int patient_disease_length1 = patient_disease_length + 1;
                        string finalNewList2 = patientInfo.Remove(0, patient_disease_length1);
                        textDisease.Text = patient_disease;
                        disease.Text = patient_disease;

                        patientInfo = finalNewList2;

                        string patient_deviceID = patientInfo.Substring(0, patientInfo.IndexOf("`"));
                        int patient_deviceID_length = patient_deviceID.Length;
                        int patient_deviceID_length1 = patient_deviceID_length + 1;
                        string finalNewList2a = patientInfo.Remove(0, patient_deviceID_length1);
                        textDeviceID.Text = patient_deviceID;
                        deviceid.Text = patient_deviceID;

                        patientInfo = finalNewList2a;


                        string patient_room = patientInfo.Substring(0, patientInfo.IndexOf("`"));
                        int patient_room_length = patient_room.Length;
                        int patient_room_length1 = patient_room_length + 1;
                        string finalNewList2b = patientInfo.Remove(0, patient_room_length1);
                        textRoom.Text = patient_room;
                        room.Text = patient_room;

                        patientInfo = finalNewList2b;

                        string patient_encoder = patientInfo.Substring(0, patientInfo.IndexOf("`"));
                        int patient_encoder_length = patient_encoder.Length;
                        int patient_encoder_length1 = patient_encoder_length + 1;
                        string finalNewList2c = patientInfo.Remove(0, patient_encoder_length1);
                        textEncoder.Text = patient_encoder;
                        encoder.Text = patient_encoder;

                        patientInfo = finalNewList2c;

                        string patient_admit = patientInfo.Substring(0, patientInfo.IndexOf("`"));
                        int patient_admit_length = patient_admit.Length;
                        int patient_admit_length1 = patient_admit_length + 1;
                        string finalNewList2d = patientInfo.Remove(0, patient_admit_length1);
                        textAdmit.Text = patient_admit;

                        patientInfo = finalNewList2d;

                        string patient_dismiss = patientInfo.Substring(0, patientInfo.IndexOf("`"));
                        int patient_dismiss_length = patient_dismiss.Length;
                        int patient_dismiss_length1 = patient_dismiss_length + 1;
                        string finalNewList2e = patientInfo.Remove(0, patient_dismiss_length1);
                        textDismiss.Text = patient_dismiss;

                        if (patient_dismiss == "")
                        {
                            DimissedLabel.Visibility = Visibility.Hidden;
                        }

                        patientInfo = "";

                    }
                }
            }

            else
            {
                MessageBox.Show("Failed to retrieve patient information.", "Retrieve Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void EditPatientInfo_Click(object sender, RoutedEventArgs e)
        {
            deviceid.Items.Clear();
            room.Items.Clear();
            encoder.Items.Clear();

            getDeviceID();
            getRoom();
            getEncoder();

            room.SelectedItem = textRoom.Text;
            deviceid.SelectedItem = textDeviceID.Text;
            encoder.SelectedItem = textEncoder.Text;

            editPatientInfo.Visibility = Visibility.Hidden;
            saveInfo.Visibility = Visibility.Visible;
            cancel.Visibility = Visibility.Visible;

            name.Visibility = Visibility.Visible;
            gender.Visibility = Visibility.Visible;
            bdate.Visibility = Visibility.Visible;
            address.Visibility = Visibility.Visible;
            number.Visibility = Visibility.Visible;
            disease.Visibility = Visibility.Visible;
            deviceid.Visibility = Visibility.Visible;
            room.Visibility = Visibility.Visible;
            encoder.Visibility = Visibility.Visible;

            textName.Visibility = Visibility.Hidden;
            textGender.Visibility = Visibility.Hidden;
            textBdate.Visibility = Visibility.Hidden;
            textAddress.Visibility = Visibility.Hidden;
            textNumber.Visibility = Visibility.Hidden;
            textDisease.Visibility = Visibility.Hidden;
            textDeviceID.Visibility = Visibility.Hidden;
            textRoom.Visibility = Visibility.Hidden;
            textEncoder.Visibility = Visibility.Hidden;

            notEdited.Visibility = Visibility.Hidden;
            edited.Visibility = Visibility.Visible;
            editedDisease.Visibility = Visibility.Visible;
            notEditedDisease.Visibility = Visibility.Hidden;
        }

        private void Cancel_Click(object sender, RoutedEventArgs e)
        {
            cancel.Visibility = Visibility.Hidden;
            saveInfo.Visibility = Visibility.Hidden;
            editPatientInfo.Visibility = Visibility.Visible;

            name.Visibility = Visibility.Hidden;
            gender.Visibility = Visibility.Hidden;
            bdate.Visibility = Visibility.Hidden;
            address.Visibility = Visibility.Hidden;
            number.Visibility = Visibility.Hidden;
            disease.Visibility = Visibility.Hidden;
            deviceid.Visibility = Visibility.Hidden;
            room.Visibility = Visibility.Hidden;
            encoder.Visibility = Visibility.Hidden;

            textName.Visibility = Visibility.Visible;
            textGender.Visibility = Visibility.Visible;
            textBdate.Visibility = Visibility.Visible;
            textAddress.Visibility = Visibility.Visible;
            textNumber.Visibility = Visibility.Visible;
            textDisease.Visibility = Visibility.Visible;
            textDeviceID.Visibility = Visibility.Visible;
            textRoom.Visibility = Visibility.Visible;
            textEncoder.Visibility = Visibility.Visible;

            notEdited.Visibility = Visibility.Visible;
            edited.Visibility = Visibility.Hidden;
            editedDisease.Visibility = Visibility.Hidden;
            notEditedDisease.Visibility = Visibility.Visible;
        }

        private async void SaveInfo_Click(object sender, RoutedEventArgs e)
        {

            var DialogResult = MessageBox.Show("Are you sure you want to update the information of this Patient?", "Update Patient Information", MessageBoxButton.YesNo, MessageBoxImage.Question, MessageBoxResult.No);

            if (DialogResult == MessageBoxResult.Yes)
            {

                var url = "http://192.168.43.66:8080/smartcare/updatePatientInfoAdmin.php";

                var client = new HttpClient();


                string namestring = name.Text;
                string genderstring = selectedGender;
                string birthdatestring = bdate.Text;
                string addressstring = address.Text;
                string numberstring = number.Text;
                string diseasestring = disease.Text;
                string roomstring = room.Text;
                string devicestring = deviceid.Text;
                string patientstring = id.Text;
                string encoderstring = encoder.Text;



                var data = new Dictionary<string, string>{
                        { "name", namestring},
                        { "gender", selectedGender},
                        { "birthdate", birthdatestring},
                        { "address", addressstring},
                        { "number", numberstring},
                        { "disease", diseasestring},
                        { "room", roomstring},
                        { "device", devicestring},
                        { "patientID", patientstring},
                        { "encoder", encoderstring}
                    };

                var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
                var content = await httpResponseMessage.Content.ReadAsStringAsync();

                if (content != "" || content != "<br")
                {

                    if (content == "device not exist")
                    {
                        MessageBox.Show("Device ID does not exist anymore.", "Device Error", MessageBoxButton.OK, MessageBoxImage.Error);
                        deviceid.Items.Clear();

                        getDeviceID();
                    }

                    else if (content == "device taken")
                    {
                        MessageBox.Show("Device ID is already taken.", "Device Error", MessageBoxButton.OK, MessageBoxImage.Error);

                        deviceid.Items.Clear();

                        getDeviceID();
                    }

                    else if (content == "room not exist")
                    {
                        MessageBox.Show("Room does not exist anymore.", "Room Error", MessageBoxButton.OK, MessageBoxImage.Error);

                        room.Items.Clear();

                        getRoom();
                    }

                    else if (content == "room taken")
                    {
                        MessageBox.Show("Room is already taken.", "Room Error", MessageBoxButton.OK, MessageBoxImage.Error);

                        room.Items.Clear();

                        getRoom();
                    }

                    else if (content == "encoder not exist")
                    {
                        MessageBox.Show("Encoder does not exist anymore.", "Encoder Error", MessageBoxButton.OK, MessageBoxImage.Error);

                        encoder.Items.Clear();

                        getEncoder();
                    }

                    else
                    {
                        var patientInfo = content;

                        for (; ; )
                        {
                            if (patientInfo == "" || patientInfo == " ")
                            {
                                break;
                            }

                            else
                            {
                                string patient_id = patientInfo.Substring(0, patientInfo.IndexOf("`"));
                                int patient_id_length = patient_id.Length;
                                int patient_id_length1 = patient_id_length + 1;
                                string finalNewLista = patientInfo.Remove(0, patient_id_length1);
                                id.Text = patient_id;

                                patientInfo = finalNewLista;

                                string patient_name = patientInfo.Substring(0, patientInfo.IndexOf("`"));
                                int patient_name_length = patient_name.Length;
                                int patient_name_length1 = patient_name_length + 1;
                                string finalNewList = patientInfo.Remove(0, patient_name_length1);
                                textName.Text = patient_name;
                                name.Text = patient_name;

                                patientInfo = finalNewList;

                                string patient_gender = patientInfo.Substring(0, patientInfo.IndexOf("`"));
                                int patient_gender_length = patient_gender.Length;
                                int patient_gender_length1 = patient_gender_length + 1;
                                string finalNewList1a = patientInfo.Remove(0, patient_gender_length1);
                                textGender.Text = patient_gender;
                                gender.Text = patient_gender;

                                patientInfo = finalNewList1a;

                                string patient_birthdate = patientInfo.Substring(0, patientInfo.IndexOf("`"));
                                int patient_birthdate_length = patient_birthdate.Length;
                                int patient_birthdate_length1 = patient_birthdate_length + 1;
                                string finalNewList1b = patientInfo.Remove(0, patient_birthdate_length1);
                                textBdate.Text = patient_birthdate;
                                bdate.Text = patient_birthdate;

                                patientInfo = finalNewList1b;

                                string patient_address = patientInfo.Substring(0, patientInfo.IndexOf("`"));
                                int patient_address_length = patient_address.Length;
                                int patient_address_length1 = patient_address_length + 1;
                                string finalNewList4 = patientInfo.Remove(0, patient_address_length1);

                                if (patient_address_length >= 37)
                                {
                                    string formattedAddress = patient_address.Substring(0, 37);
                                    textAddress.Text = formattedAddress + "...";
                                }

                                else
                                {
                                    textAddress.Text = patient_address;
                                }

                                address.Text = patient_address;


                                patientInfo = finalNewList4;

                                string patient_number = patientInfo.Substring(0, patientInfo.IndexOf("`"));
                                int patient_number_length = patient_number.Length;
                                int patient_number_length1 = patient_number_length + 1;
                                string finalNewList1c = patientInfo.Remove(0, patient_number_length1);
                                textNumber.Text = patient_number;
                                number.Text = patient_number;

                                patientInfo = finalNewList1c;

                                string patient_disease = patientInfo.Substring(0, patientInfo.IndexOf("`"));
                                int patient_disease_length = patient_disease.Length;
                                int patient_disease_length1 = patient_disease_length + 1;
                                string finalNewList2 = patientInfo.Remove(0, patient_disease_length1);
                                textDisease.Text = patient_disease;
                                disease.Text = patient_disease;

                                patientInfo = finalNewList2;

                                string patient_deviceID = patientInfo.Substring(0, patientInfo.IndexOf("`"));
                                int patient_deviceID_length = patient_deviceID.Length;
                                int patient_deviceID_length1 = patient_deviceID_length + 1;
                                string finalNewList2a = patientInfo.Remove(0, patient_deviceID_length1);
                                textDeviceID.Text = patient_deviceID;
                                deviceid.Text = patient_deviceID;

                                patientInfo = finalNewList2a;


                                string patient_room = patientInfo.Substring(0, patientInfo.IndexOf("`"));
                                int patient_room_length = patient_room.Length;
                                int patient_room_length1 = patient_room_length + 1;
                                string finalNewList2b = patientInfo.Remove(0, patient_room_length1);
                                textRoom.Text = patient_room;
                                room.Text = patient_room;

                                patientInfo = finalNewList2b;

                                string patient_encoder = patientInfo.Substring(0, patientInfo.IndexOf("`"));
                                int patient_encoder_length = patient_encoder.Length;
                                int patient_encoder_length1 = patient_encoder_length + 1;
                                string finalNewList2d = patientInfo.Remove(0, patient_encoder_length1);
                                textEncoder.Text = patient_encoder;
                                encoder.Text = patient_encoder;

                                patientInfo = finalNewList2d;

                                string patient_admit = patientInfo.Substring(0, patientInfo.IndexOf("`"));
                                int patient_admit_length = patient_admit.Length;
                                int patient_admit_length1 = patient_admit_length + 1;
                                string finalNewList2de = patientInfo.Remove(0, patient_admit_length1);
                                textAdmit.Text = patient_admit;

                                patientInfo = finalNewList2de;

                                string patient_dismiss = patientInfo.Substring(0, patientInfo.IndexOf("`"));
                                int patient_dismiss_length = patient_dismiss.Length;
                                int patient_dismiss_length1 = patient_dismiss_length + 1;
                                string finalNewList2e = patientInfo.Remove(0, patient_dismiss_length1);
                                textDismiss.Text = patient_dismiss;

                                if (textDismiss.Text == "")
                                {
                                    DimissedLabel.Visibility = Visibility.Hidden;
                                }

                                patientInfo = "";

                            }
                        }


                        MessageBox.Show("Patient Information Updated Successfully");

                        cancel.Visibility = Visibility.Hidden;
                        saveInfo.Visibility = Visibility.Hidden;
                        editPatientInfo.Visibility = Visibility.Visible;

                        name.Visibility = Visibility.Hidden;
                        gender.Visibility = Visibility.Hidden;
                        bdate.Visibility = Visibility.Hidden;
                        address.Visibility = Visibility.Hidden;
                        number.Visibility = Visibility.Hidden;
                        disease.Visibility = Visibility.Hidden;
                        deviceid.Visibility = Visibility.Hidden;
                        room.Visibility = Visibility.Hidden;
                        encoder.Visibility = Visibility.Hidden;

                        textName.Visibility = Visibility.Visible;
                        textGender.Visibility = Visibility.Visible;
                        textBdate.Visibility = Visibility.Visible;
                        textAddress.Visibility = Visibility.Visible;
                        textNumber.Visibility = Visibility.Visible;
                        textDisease.Visibility = Visibility.Visible;
                        textDeviceID.Visibility = Visibility.Visible;
                        textRoom.Visibility = Visibility.Visible;
                        textEncoder.Visibility = Visibility.Visible;

                        notEdited.Visibility = Visibility.Visible;
                        edited.Visibility = Visibility.Hidden;
                        editedDisease.Visibility = Visibility.Hidden;
                        notEditedDisease.Visibility = Visibility.Visible;

                        Home.instance.patientGrid.Items.Clear();
                        Home.instance.getAllPatientData();
                    }
                }

                else
                {
                    MessageBox.Show("Failure to update patient information.", "Update Error", MessageBoxButton.OK, MessageBoxImage.Error);
                }
            }
        }

        private void Gender_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            selectedGender = ((System.Windows.Controls.ComboBoxItem)gender.SelectedItem).Content as string;
        }
        private void TypeNumericValidation(object sender, TextCompositionEventArgs e)
        {
            e.Handled = new Regex("[^0-9]+").IsMatch(e.Text);
        }

        private async void getDeviceID()
        {
            var url1 = "http://192.168.43.66:8080/smartcare/getDevicesIDAdmin.php";

            var client1 = new HttpClient();

            var data1 = new Dictionary<string, string>
{
                {"patient_id", patientID}
                };

            var httpResponseMessage1 = await client1.PostAsync(url1, new FormUrlEncodedContent(data1));
            var deviceID = await httpResponseMessage1.Content.ReadAsStringAsync();

            if (deviceID != "" || deviceID != " " || deviceID != "failure")
            {

                int i = 0;
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

            else {
                MessageBox.Show("Failed to retrieve user information.", "Retrieve Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private async void getRoom()
        {
            int f = 0;

            var url1 = "http://192.168.43.66:8080/smartcare/getRoomAdmin.php";

            var client1 = new HttpClient();

            var data1 = new Dictionary<string, string>
{
                {"patient_id", patientID}
                };

            var httpResponseMessage1 = await client1.PostAsync(url1, new FormUrlEncodedContent(data1));
            var roomNumberResponse = await httpResponseMessage1.Content.ReadAsStringAsync();

            if (roomNumberResponse != "" || roomNumberResponse != " " || roomNumberResponse != "failure")
            {
                var roomNumber = roomNumberResponse;

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

        private async void getEncoder()
        {
            int e = 0;

            var url1 = "http://192.168.43.66:8080/smartcare/getStaffNamesAdmin.php";

            var client1 = new HttpClient();

            var data1 = new Dictionary<string, string>
{
                {"patient_id", patientID}
                };

            var httpResponseMessage1 = await client1.PostAsync(url1, new FormUrlEncodedContent(data1));
            var encoderList = await httpResponseMessage1.Content.ReadAsStringAsync();

            if (encoderList != "" || encoderList != " " || encoderList != "failure")
            {
                var staffName = encoderList;

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
