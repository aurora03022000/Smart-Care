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
    /// Interaction logic for registerRoom.xaml
    /// </summary>
    public partial class registerRoom : Window
    {
        public registerRoom()
        {
            InitializeComponent();
        }

        private void TypeNumericValidation(object sender, TextCompositionEventArgs e)
        {
            e.Handled = new Regex("[^0-9]+").IsMatch(e.Text);
        }

        private void ipAddress_TextChanged(object sender, TextChangedEventArgs e)
        {

        }

        private async void Reg_Button_Click(object sender, RoutedEventArgs e)
        {
            var url = "http://192.168.254.114:8080/smartcare/addRoom.php";

            var client = new HttpClient();

            var data = new Dictionary<string, string>{
                {"room_number", room.Text}
            };

            var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
            var content = await httpResponseMessage.Content.ReadAsStringAsync();

            if(content == "success")
            {
                MessageBox.Show("Room Number added successfully");
                Home.instance.roomGrid.Items.Clear();
                Home.instance.getAllRoom();
                this.Close();

            }

            else if(content == "taken")
            {
                MessageBox.Show("Room number already exist.", "Room Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }


            else
            {
                MessageBox.Show("Failure to add room.", "Room Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }
    }
}
