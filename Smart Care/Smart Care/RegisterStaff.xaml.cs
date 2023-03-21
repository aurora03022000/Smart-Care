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
    /// Interaction logic for RegisterStaff.xaml
    /// </summary>
    public partial class RegisterStaff : Window
    {

        string selectedGender;
        string substituteForGender = "empty";

        public static RegisterStaff instance;

        public RegisterStaff()
        {
            InitializeComponent();
            gender.SelectedIndex = -1;

            instance = this;
        }

        private async void RegisterStaff_Button_Click(object sender, RoutedEventArgs e)
        {
            string usernamestring = username.Text;
            string passwordstring = password.Password;
            string namestring = name.Text;
            string birthdatestring = bdate.Text;
            string genderstring = selectedGender;
            string addressstring = address.Text;
            string numberstring = number.Text;

            if(usernamestring == "")
            {
                MessageBox.Show("Username field is required");
                username.Focus();
            }

            else if(passwordstring == "")
            {
                MessageBox.Show("Password field is required");
                password.Focus();
            }

            else if (namestring == "")
            {
                MessageBox.Show("Name field is required");
                name.Focus();
            }

            else if (substituteForGender == "empty")
            {
                MessageBox.Show("Gender field is required");
                gender.Focus();
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

            else
            {
                if (usernamestring.Length < 6)
                {
                    MessageBox.Show("Username must be greater than 6.", "Username Error", MessageBoxButton.OK, MessageBoxImage.Error);
                    username.Text = "";
                    username.Focus();
                }

                else
                {

                    if (passwordstring.Length < 8)
                    {
                        MessageBox.Show("Password must be greater than 8.", "Password Error", MessageBoxButton.OK, MessageBoxImage.Error);
                        password.Password = "";
                        password.Focus();
                    }

                    if(Regex.IsMatch(passwordstring, @"^[a-zA-Z]+$"))
                    {
                        MessageBox.Show("Password must contain letters.", "Password Error", MessageBoxButton.OK, MessageBoxImage.Error);
                        password.Password = "";
                        password.Focus();
                    }

                    else if(Regex.IsMatch(passwordstring, @"^[0-9]+$"))
                    {
                        MessageBox.Show("Password must contain numbers.", "Password Error", MessageBoxButton.OK, MessageBoxImage.Error);
                        password.Password = "";
                        password.Focus();
                    }

                    else
                    {
                        var DialogResult = MessageBox.Show("Are you sure you want to register this medical staff?", "Register", MessageBoxButton.YesNo, MessageBoxImage.Question, MessageBoxResult.No);

                        if (DialogResult == MessageBoxResult.Yes)
                        {
                            var url0 = "http://192.168.43.66:8080/smartcare/checkUsernameExist.php";

                            var client0 = new HttpClient();

                            var data0 = new Dictionary<string, string>{
                                {"username", username.Text.ToString()}
                                };

                            var httpResponseMessage0 = await client0.PostAsync(url0, new FormUrlEncodedContent(data0));
                            var content0 = await httpResponseMessage0.Content.ReadAsStringAsync();

                            if (content0 == "exist")
                            {
                                MessageBox.Show("Username is already taken.", "Username Error", MessageBoxButton.OK, MessageBoxImage.Error);
                                username.Text = "";
                                username.Focus();
                            }

                            else if (content0 == "success")
                            {
                                var url = "http://192.168.43.66:8080/smartcare/registerStaff.php";

                                var client = new HttpClient();

                                var data = new Dictionary<string, string>{
                                { "username", usernamestring},
                                { "password", passwordstring},
                                { "staff_name", namestring},
                                { "staff_birthdate", birthdatestring},
                                { "staff_gender", genderstring},
                                { "staff_address", addressstring},
                                { "staff_number", numberstring}
                            };

                                var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
                                var content = await httpResponseMessage.Content.ReadAsStringAsync();

                                if (content == "success")
                                {
                                    MessageBox.Show("Medical Staff have been registered successfully");
                                    Home.instance.medicalStaffGrid.Items.Clear();
                                    Home.instance.getAllStaffData();
                                    username.Text = "";
                                    password.Password = "";
                                    name.Text = "";
                                    gender.SelectedIndex = -1;
                                    bdate.Text = "";
                                    address.Text = "";
                                    number.Text = "";
                                }

                                else
                                {
                                    MessageBox.Show("Registration failed.", "Registration Error", MessageBoxButton.OK, MessageBoxImage.Error);
                                }
                            }


                            else
                            {
                                MessageBox.Show("Error encounter.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                            }

                        }
                         
                    }
                }
             }
        }

        private void Gender_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            substituteForGender = "not empty";

            if (gender.SelectedIndex != -1)
            {
                selectedGender = ((System.Windows.Controls.ComboBoxItem)gender.SelectedItem).Content as string;
            }
        }

        private void TypeNumericValidation(object sender, TextCompositionEventArgs e)
        {
            e.Handled = new Regex("[^0-9]+").IsMatch(e.Text);
        }

       
        
    }
}
