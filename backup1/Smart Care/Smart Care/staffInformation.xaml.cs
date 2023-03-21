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
    /// Interaction logic for staffInformation.xaml
    /// </summary>
    public partial class staffInformation : Window
    {
        string selectedGender;

        public staffInformation()
        {
            InitializeComponent();

            editInfo.Margin = new Thickness(0, -73, 0, 0);

            HttpClient client = new HttpClient();
            client.BaseAddress = new Uri("http://192.168.254.114:8080/smartcare/");
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


            HttpClient client1 = new HttpClient();
            client1.BaseAddress = new Uri("http://192.168.254.114:8080/smartcare/");
            client1.DefaultRequestHeaders.Accept.Add(
               new MediaTypeWithQualityHeaderValue("application/json"));

            HttpResponseMessage response1 = client1.GetAsync("getStaffInformationAdmin.php").Result;
            if (response1.IsSuccessStatusCode)
            {
                var medicalStaffInfo = response1.Content.ReadAsStringAsync().Result;

                for (; ; )
                {
                    if (medicalStaffInfo == "" || medicalStaffInfo == " ")
                    {
                        break;
                    }

                    else
                    {
                        string medicalStaff_date_registered = medicalStaffInfo.Substring(0, medicalStaffInfo.IndexOf("`"));
                        int medicalStaff_date_registered_length = medicalStaff_date_registered.Length;
                        int medicalStaff_date_registered_length1 = medicalStaff_date_registered_length + 1;
                        string finalNewLista = medicalStaffInfo.Remove(0, medicalStaff_date_registered_length1);
                        dateRegistered.Content = "Registered since " + medicalStaff_date_registered;

                        medicalStaffInfo = finalNewLista;

                        string medicalStaff_date_deleted = medicalStaffInfo.Substring(0, medicalStaffInfo.IndexOf("`"));
                        int medicalStaff_date_deleted_length = medicalStaff_date_deleted.Length;
                        int medicalStaff_date_deleted_length1 = medicalStaff_date_deleted_length + 1;
                        string finalNewListab = medicalStaffInfo.Remove(0, medicalStaff_date_deleted_length1);

                        if(medicalStaff_date_deleted == "")
                        {
                            dateRegistered.Content = "Registered since " + medicalStaff_date_registered;
                        }

                        else
                        {
                            dateRegistered.Visibility = Visibility.Hidden;

                            dateRegistered1.Content = "Registered since " + medicalStaff_date_registered;
                            dateDeleted.Content = "Deleted since " + medicalStaff_date_deleted;

                            dateRegistered1.Visibility = Visibility.Visible;
                            dateDeleted.Visibility = Visibility.Visible;

                        }
                        

                        medicalStaffInfo = finalNewListab;


                        string medicalStaff_id = medicalStaffInfo.Substring(0, medicalStaffInfo.IndexOf("`"));
                        int medicalStaff_id_length = medicalStaff_id.Length;
                        int medicalStaff_id_length1 = medicalStaff_id_length + 1;
                        string finalNewList = medicalStaffInfo.Remove(0, medicalStaff_id_length1);
                        id.Text = medicalStaff_id;

                        medicalStaffInfo = finalNewList;

                        string medicalStaff_username = medicalStaffInfo.Substring(0, medicalStaffInfo.IndexOf("`"));
                        int medicalStaff_username_length = medicalStaff_username.Length;
                        int medicalStaff_username_length1 = medicalStaff_username_length + 1;
                        string finalNewList1a = medicalStaffInfo.Remove(0, medicalStaff_username_length1);
                        textUsername.Text = medicalStaff_username;
                        username.Text = medicalStaff_username;

                        medicalStaffInfo = finalNewList1a;

                        string medicalStaff_password = medicalStaffInfo.Substring(0, medicalStaffInfo.IndexOf("`"));
                        int medicalStaff_password_length = medicalStaff_password.Length;
                        int medicalStaff_password_length1 = medicalStaff_password_length + 1;
                        string finalNewList1b = medicalStaffInfo.Remove(0, medicalStaff_password_length1);
                        String textPasswordValue = textPassword.Text;
                        String newText = "";

                        for (int l = 1; l < medicalStaff_password_length1; l++ )
                        {
                            if(l == 0)
                            {
                                newText = textPasswordValue + "•";
                            }

                            else
                            {
                                newText = newText + "•";
                            }
                        }
                        textPassword.Text = newText;
                        password.Password = medicalStaff_password;

                        medicalStaffInfo = finalNewList1b;

                        string medicalStaff_name = medicalStaffInfo.Substring(0, medicalStaffInfo.IndexOf("`"));
                        int medicalStaff_name_length = medicalStaff_name.Length;
                        int medicalStaff_name_length1 = medicalStaff_name_length + 1;
                        string finalNewList1c = medicalStaffInfo.Remove(0, medicalStaff_name_length1);
                        textName.Text = medicalStaff_name;
                        name.Text = medicalStaff_name;

                        medicalStaffInfo = finalNewList1c;

                        string medicalStaff_gender = medicalStaffInfo.Substring(0, medicalStaffInfo.IndexOf("`"));
                        int medicalStaff_gender_length = medicalStaff_gender.Length;
                        int medicalStaff_gender_length1 = medicalStaff_gender_length + 1;
                        string finalNewList2 = medicalStaffInfo.Remove(0, medicalStaff_gender_length1);
                        textGender.Text = medicalStaff_gender;
                        gender.Text = medicalStaff_gender;

                        medicalStaffInfo = finalNewList2;

                        string medicalStaff_bdate = medicalStaffInfo.Substring(0, medicalStaffInfo.IndexOf("`"));
                        int medicalStaff_bdate_length = medicalStaff_bdate.Length;
                        int medicalStaff_bdate_length1 = medicalStaff_bdate_length + 1;
                        string finalNewList2a = medicalStaffInfo.Remove(0, medicalStaff_bdate_length1);
                        textBdate.Text = medicalStaff_bdate;
                        bdate.Text = medicalStaff_bdate;

                        medicalStaffInfo = finalNewList2a;

                        string medicalStaff_address = medicalStaffInfo.Substring(0, medicalStaffInfo.IndexOf("`"));
                        int medicalStaff_address_length = medicalStaff_address.Length;
                        int medicalStaff_address_length1 = medicalStaff_address_length + 1;
                        string finalNewList4 = medicalStaffInfo.Remove(0, medicalStaff_address_length1);
                        
                        if(medicalStaff_address_length >= 37)
                        {
                            string formattedAddress = medicalStaff_address.Substring(0, 37);
                            textAddress.Text = formattedAddress + "...";
                        }

                        else
                        {
                            textAddress.Text = medicalStaff_address;
                        }
                        address.Text = medicalStaff_address;

                        medicalStaffInfo = finalNewList4;
                        textNumber.Text = finalNewList4;
                        number.Text = finalNewList4;
                        medicalStaffInfo = "";

                    }
                }
            }
            else
            {
                MessageBox.Show("Error Code" + response.StatusCode + " : Message - " + response.ReasonPhrase);
            }
        }

        private void setUserProfile(string profileUrl)
        {
            profilePicture.ImageSource = new BitmapImage(new Uri("http://192.168.254.114:8080/smartcare/" + profileUrl));
        }

        private void TypeNumericValidation(object sender, TextCompositionEventArgs e)
        {
            e.Handled = new Regex("[^0-9]+").IsMatch(e.Text);
        }

        private void editInfo_Click(object sender, RoutedEventArgs e)
        {
            password.Visibility = Visibility.Visible;
            name.Visibility = Visibility.Visible;
            gender.Visibility = Visibility.Visible;
            bdate.Visibility = Visibility.Visible;
            address.Visibility = Visibility.Visible;
            edited.Visibility = Visibility.Visible;
            number.Visibility = Visibility.Visible;

            textPassword.Visibility = Visibility.Hidden;
            textName.Visibility = Visibility.Hidden;
            textGender.Visibility = Visibility.Hidden;
            textBdate.Visibility = Visibility.Hidden;
            textAddress.Visibility = Visibility.Hidden;
            notEdited.Visibility = Visibility.Hidden;
            textNumber.Visibility = Visibility.Hidden;

            editInfo.Margin = new Thickness(0, -33, 0, 0);

            editInfo.Visibility = Visibility.Hidden;
            saveInfo.Visibility = Visibility.Visible;
            cancel.Visibility = Visibility.Visible;

        }

        private async void saveInfo_Click(object sender, RoutedEventArgs e)
        {
            string idString = id.Text;
            string passwordString = password.Password;
            string nameString = name.Text;
            string genderString = selectedGender;
            string birthdateString = bdate.Text;
            string addressString = address.Text;
            string numberString = number.Text;

            if(password.Password.Length < 8)
            {
                MessageBox.Show("Password must be greater than 8 characters in long.", "Update Error", MessageBoxButton.OK, MessageBoxImage.Error);
                password.Focus();
            }


            if (Regex.IsMatch(passwordString, @"^[a-zA-Z]+$"))
            {
                MessageBox.Show("Password must contain numbers.", "Password Error", MessageBoxButton.OK, MessageBoxImage.Error);
                password.Password = "";
                password.Focus();
            }

            else if (Regex.IsMatch(passwordString, @"^[0-9]+$"))
            {
                MessageBox.Show("Password must contain letters.", "Password Error", MessageBoxButton.OK, MessageBoxImage.Error);
                password.Password = "";
                password.Focus();
            }


            else
            {
                var DialogResult = MessageBox.Show("Are you sure you want to update the information of this Medical Staff?", "Update Medical Staff Information", MessageBoxButton.YesNo, MessageBoxImage.Question, MessageBoxResult.No);

                if (DialogResult == MessageBoxResult.Yes)
                {
                    var url = "http://192.168.254.114:8080/smartcare/updateStaffInformationAdmin.php";

                    var client = new HttpClient();



                    var data = new Dictionary<string, string>{
                        { "currentUser", idString},
                        { "passwordInput", passwordString},
                        { "nameInput", nameString},
                        { "birthdateInput", birthdateString},
                        { "genderInput", genderString},
                        { "addressInput", addressString},
                        { "numberInput", numberString}
                    };

                    var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
                    var content = await httpResponseMessage.Content.ReadAsStringAsync();

                    if (content != "" || content != "<br")
                    {
                        MessageBox.Show("Medical Staff Information have been updated successfully");
                        password.Visibility = Visibility.Hidden;
                        name.Visibility = Visibility.Hidden;
                        gender.Visibility = Visibility.Hidden;
                        bdate.Visibility = Visibility.Hidden;
                        address.Visibility = Visibility.Hidden;
                        edited.Visibility = Visibility.Hidden;
                        number.Visibility = Visibility.Hidden;

                        textPassword.Visibility = Visibility.Visible;
                        textName.Visibility = Visibility.Visible;
                        textGender.Visibility = Visibility.Visible;
                        textBdate.Visibility = Visibility.Visible;
                        textAddress.Visibility = Visibility.Visible;
                        notEdited.Visibility = Visibility.Visible;
                        textNumber.Visibility = Visibility.Visible;

                        editInfo.Margin = new Thickness(0, -73, 0, 0);

                        editInfo.Visibility = Visibility.Visible;
                        saveInfo.Visibility = Visibility.Hidden;
                        cancel.Visibility = Visibility.Hidden;

                        var medicalStaffInfo1 = content;

                        for (; ; )
                        {
                            if (medicalStaffInfo1 == "" || medicalStaffInfo1 == " ")
                            {
                                break;
                            }

                            else
                            {
                                string medicalStaff_id1 = medicalStaffInfo1.Substring(0, medicalStaffInfo1.IndexOf("`"));
                                int medicalStaff_id1_length = medicalStaff_id1.Length;
                                int medicalStaff_id1_length1 = medicalStaff_id1_length + 1;
                                string finalNewList = medicalStaffInfo1.Remove(0, medicalStaff_id1_length1);
                                id.Text = medicalStaff_id1;

                                medicalStaffInfo1 = finalNewList;

                                string medicalStaff_username1 = medicalStaffInfo1.Substring(0, medicalStaffInfo1.IndexOf("`"));
                                int medicalStaff_username1_length = medicalStaff_username1.Length;
                                int medicalStaff_username1_length1 = medicalStaff_username1_length + 1;
                                string finalNewList1a = medicalStaffInfo1.Remove(0, medicalStaff_username1_length1);
                                textUsername.Text = medicalStaff_username1;
                                username.Text = medicalStaff_username1;

                                medicalStaffInfo1 = finalNewList1a;

                                string medicalStaff_password1 = medicalStaffInfo1.Substring(0, medicalStaffInfo1.IndexOf("`"));
                                int medicalStaff_password1_length = medicalStaff_password1.Length;
                                int medicalStaff_password1_length1 = medicalStaff_password1_length + 1;
                                string finalNewList1b = medicalStaffInfo1.Remove(0, medicalStaff_password1_length1);
                                String textPasswordValue = textPassword.Text;
                                String newText = "";

                                for (int l = 1; l < medicalStaff_password1_length1; l++)
                                {
                                    if (l == 0)
                                    {
                                        newText = textPasswordValue + "•";
                                    }

                                    else
                                    {
                                        newText = newText + "•";
                                    }
                                }
                                textPassword.Text = newText;
                                password.Password = medicalStaff_password1;

                                medicalStaffInfo1 = finalNewList1b;

                                string medicalStaff_name1 = medicalStaffInfo1.Substring(0, medicalStaffInfo1.IndexOf("`"));
                                int medicalStaff_name1_length = medicalStaff_name1.Length;
                                int medicalStaff_name1_length1 = medicalStaff_name1_length + 1;
                                string finalNewList1c = medicalStaffInfo1.Remove(0, medicalStaff_name1_length1);
                                textName.Text = medicalStaff_name1;
                                name.Text = medicalStaff_name1;

                                medicalStaffInfo1 = finalNewList1c;

                                string medicalStaff_gender1 = medicalStaffInfo1.Substring(0, medicalStaffInfo1.IndexOf("`"));
                                int medicalStaff_gender1_length = medicalStaff_gender1.Length;
                                int medicalStaff_gender1_length1 = medicalStaff_gender1_length + 1;
                                string finalNewList2 = medicalStaffInfo1.Remove(0, medicalStaff_gender1_length1);
                                textGender.Text = medicalStaff_gender1;
                                gender.Text = medicalStaff_gender1;

                                medicalStaffInfo1 = finalNewList2;

                                string medicalStaff_bdate1 = medicalStaffInfo1.Substring(0, medicalStaffInfo1.IndexOf("`"));
                                int medicalStaff_bdate1_length = medicalStaff_bdate1.Length;
                                int medicalStaff_bdate1_length1 = medicalStaff_bdate1_length + 1;
                                string finalNewList2a = medicalStaffInfo1.Remove(0, medicalStaff_bdate1_length1);
                                textBdate.Text = medicalStaff_bdate1;
                                bdate.Text = medicalStaff_bdate1;

                                medicalStaffInfo1 = finalNewList2a;

                                string medicalStaff_address1 = medicalStaffInfo1.Substring(0, medicalStaffInfo1.IndexOf("`"));
                                int medicalStaff_address1_length = medicalStaff_address1.Length;
                                int medicalStaff_address1_length1 = medicalStaff_address1_length + 1;
                                string finalNewList4 = medicalStaffInfo1.Remove(0, medicalStaff_address1_length1);

                                if (medicalStaff_address1_length >= 37)
                                {
                                    string formattedAddress = medicalStaff_address1.Substring(0, 37);
                                    textAddress.Text = formattedAddress + "...";
                                }

                                else
                                {
                                    textAddress.Text = medicalStaff_address1;
                                }
                                address.Text = medicalStaff_address1;

                                medicalStaffInfo1 = finalNewList4;
                                textNumber.Text = finalNewList4;
                                number.Text = finalNewList4;
                                medicalStaffInfo1 = "";

                            }
                        }
                    }
                }

                else
                {
                    MessageBox.Show("Update Account failed.", "Update Error", MessageBoxButton.OK, MessageBoxImage.Error);
                }
            }
        }

        private void cancel_Click(object sender, RoutedEventArgs e)
        {
            password.Visibility = Visibility.Hidden;
            name.Visibility = Visibility.Hidden;
            gender.Visibility = Visibility.Hidden;
            bdate.Visibility = Visibility.Hidden;
            address.Visibility = Visibility.Hidden;
            edited.Visibility = Visibility.Hidden;
            number.Visibility = Visibility.Hidden;

            textPassword.Visibility = Visibility.Visible;
            textName.Visibility = Visibility.Visible;
            textGender.Visibility = Visibility.Visible;
            textBdate.Visibility = Visibility.Visible;
            textAddress.Visibility = Visibility.Visible;
            notEdited.Visibility = Visibility.Visible;
            textNumber.Visibility = Visibility.Visible;

            editInfo.Margin = new Thickness(0, -73, 0, 0);

            editInfo.Visibility = Visibility.Visible;
            saveInfo.Visibility = Visibility.Hidden;
            cancel.Visibility = Visibility.Hidden;
        }

        private void gender_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (gender.SelectedIndex != -1)
            {
                selectedGender = ((System.Windows.Controls.ComboBoxItem)gender.SelectedItem).Content as string;
            }
        }
    }
}
