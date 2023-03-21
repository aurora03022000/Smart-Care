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
    /// Interaction logic for patientHealthInformation.xaml
    /// </summary>
    public partial class patientHealthInformation : Window
    {
        string patientHealth1;
        string historyOrNot1;

        public patientHealthInformation()
        {
            InitializeComponent();

            patientHealth1 = Home.instance.patientHealth1;
            historyOrNot1 = Home.instance.historyOrNot;

            if(historyOrNot1 == "yes")
            {
                editPatientInfo.Visibility = Visibility.Hidden;
            }

            getPatientHealthLogs();
        }

        private async void getPatientHealthLogs()
        {
            var url = "http://192.168.254.114:8080/smartcare/getPatientHealthInformation.php";

            var client = new HttpClient();

            var data = new Dictionary<string, string>
{
                {"patient_id", patientHealth1}
            };
            var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
            var content = await httpResponseMessage.Content.ReadAsStringAsync();

            if (content != "" || content != " " || content != "failure")
            {
                var patientHealthInfo = content;

                for (; ; )
                {
                    if (patientHealthInfo == "" || patientHealthInfo == " ")
                    {
                        break;
                    }

                    else
                    {
                        string patient_name = patientHealthInfo.Substring(0, patientHealthInfo.IndexOf("`"));
                        int patient_name_length = patient_name.Length;
                        int patient_name_length1 = patient_name_length + 1;
                        string finalNewLista = patientHealthInfo.Remove(0, patient_name_length1);
                        patientNameText.Text = patient_name;

                        patientHealthInfo = finalNewLista;

                        string pulse_rate = patientHealthInfo.Substring(0, patientHealthInfo.IndexOf("`"));
                        int pulse_rate_length = pulse_rate.Length;
                        int pulse_rate_length1 = pulse_rate_length + 1;
                        string finalNewList = patientHealthInfo.Remove(0, pulse_rate_length1);
                        pulserate.Text = pulse_rate;
                        pulseRateText.Text = pulse_rate + "   Bpm";

                        patientHealthInfo = finalNewList;

                        string oxygen_saturation = patientHealthInfo.Substring(0, patientHealthInfo.IndexOf("`"));
                        int oxygen_saturation_length = oxygen_saturation.Length;
                        int oxygen_saturation_length1 = oxygen_saturation_length + 1;
                        string finalNewList1 = patientHealthInfo.Remove(0, oxygen_saturation_length1);
                        oxygen.Text = oxygen_saturation;
                        oxygenSaturationText.Text = oxygen_saturation + "   SO2";

                        patientHealthInfo = finalNewList1;

                        string body_temperature = patientHealthInfo.Substring(0, patientHealthInfo.IndexOf("`"));
                        int body_temperature_length = body_temperature.Length;
                        int body_temperature_length1 = body_temperature_length + 1;
                        string finalNewList2 = patientHealthInfo.Remove(0, body_temperature_length1);
                        bodyTemperature.Text = body_temperature;
                        bodyTemperatureText.Text = body_temperature + "   °C";

                        patientHealthInfo = finalNewList2;

                        string blood_pressure = patientHealthInfo.Substring(0, patientHealthInfo.IndexOf("`"));
                        int blood_pressure_length = blood_pressure.Length;
                        int blood_pressure_length1 = blood_pressure_length + 1;
                        string finalNewList3 = patientHealthInfo.Remove(0, blood_pressure_length1);
                        bloodPressure.Text = blood_pressure;
                        bloodPressureText.Text = blood_pressure + "   mm Hg";

                        patientHealthInfo = finalNewList3;

                        string respiratory_rate = patientHealthInfo.Substring(0, patientHealthInfo.IndexOf("`"));
                        int respiratory_rate_length = respiratory_rate.Length;
                        int respiratory_rate_length1 = respiratory_rate_length + 1;
                        string finalNewList4 = patientHealthInfo.Remove(0, respiratory_rate_length1);
                        respiratoryRate.Text = respiratory_rate;
                        respiratoryText.Text = respiratory_rate + "   Bpm";

                        patientHealthInfo = finalNewList4;

                        string temperature = patientHealthInfo.Substring(0, patientHealthInfo.IndexOf("`"));
                        int temperature_length = temperature.Length;
                        int temperature_length1 = temperature_length + 1;
                        string finalNewList5 = patientHealthInfo.Remove(0, temperature_length1);
                        roomTemperature.Text = temperature;
                        temperatureText.Text = temperature + "   °C";

                        patientHealthInfo = finalNewList5;

                        string humidity = patientHealthInfo.Substring(0, patientHealthInfo.IndexOf("`"));
                        int humidity_length = humidity.Length;
                        int humidity_length1 = humidity_length + 1;
                        string finalNewList6= patientHealthInfo.Remove(0, humidity_length1);
                        humidityRoom.Text = humidity;
                        humidityText.Text = humidity + "   %";

                        patientHealthInfo = finalNewList6;

                        airQuality.Text = patientHealthInfo;
                        airQualityText.Text = patientHealthInfo + "   PPM";

                        patientHealthInfo = "";

                    }
                }
            }
            else
            {
                MessageBox.Show("Failed to retrieve patient health records.", "Retrieve Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void editPatientInfo_Click(object sender, RoutedEventArgs e)
        {
            pulserate.Visibility = Visibility.Visible;
            oxygen.Visibility = Visibility.Visible;
            bodyTemperature.Visibility = Visibility.Visible;
            bloodPressure.Visibility = Visibility.Visible;
            respiratoryRate.Visibility = Visibility.Visible;

            roomTemperature.Visibility = Visibility.Visible;
            humidityRoom.Visibility = Visibility.Visible;
            airQuality.Visibility = Visibility.Visible;


            pulseRateText.Visibility = Visibility.Hidden;
            oxygenSaturationText.Visibility = Visibility.Hidden;
            bodyTemperatureText.Visibility = Visibility.Hidden;
            bloodPressureText.Visibility = Visibility.Hidden;
            respiratoryText.Visibility = Visibility.Hidden;

            temperatureText.Visibility = Visibility.Hidden;
            humidityText.Visibility = Visibility.Hidden;
            airQualityText.Visibility = Visibility.Hidden;

            editPatientInfo.Visibility = Visibility.Hidden;
            saveInfo.Visibility = Visibility.Visible;
            cancel.Visibility = Visibility.Visible;
        }

        private async void saveInfo_Click(object sender, RoutedEventArgs e)
        {
            var DialogResult = MessageBox.Show("Are you sure you want to update this record?", "Update", MessageBoxButton.YesNo, MessageBoxImage.Question, MessageBoxResult.No);

            if (DialogResult == MessageBoxResult.Yes)
            {
                var url = "http://192.168.254.114:8080/smartcare/updatePatientHealthRecordsAdmin.php";

                var client = new HttpClient();


                var data = new Dictionary<string, string>
                {
                    {"patient_id", patientHealth1},
                    {"pulse_rate", pulserate.Text},
                    {"oxygen_saturation", oxygen.Text},
                    {"body_temperature", bodyTemperature.Text},
                    {"blood_pressure", bloodPressure.Text},
                    {"respiratory_rate", respiratoryRate.Text},
                    {"room_temperature", roomTemperature.Text},
                    {"humidity", humidityRoom.Text},
                    {"air_quality", airQuality.Text}
                };


                var httpResponseMessage = await client.PostAsync(url, new FormUrlEncodedContent(data));
                var content = await httpResponseMessage.Content.ReadAsStringAsync();

                var patientHealthInfo = content;


                if(content != "")
                {
                    for (; ; )
                    {
                        if (patientHealthInfo == "" || patientHealthInfo == " ")
                        {
                            break;
                        }

                        else
                        {
                            string patient_name = patientHealthInfo.Substring(0, patientHealthInfo.IndexOf("`"));
                            int patient_name_length = patient_name.Length;
                            int patient_name_length1 = patient_name_length + 1;
                            string finalNewLista = patientHealthInfo.Remove(0, patient_name_length1);
                            patientNameText.Text = patient_name;

                            patientHealthInfo = finalNewLista;

                            string pulse_rate = patientHealthInfo.Substring(0, patientHealthInfo.IndexOf("`"));
                            int pulse_rate_length = pulse_rate.Length;
                            int pulse_rate_length1 = pulse_rate_length + 1;
                            string finalNewList = patientHealthInfo.Remove(0, pulse_rate_length1);
                            pulserate.Text = pulse_rate;
                            pulseRateText.Text = pulse_rate + "   Bpm";

                            patientHealthInfo = finalNewList;

                            string oxygen_saturation = patientHealthInfo.Substring(0, patientHealthInfo.IndexOf("`"));
                            int oxygen_saturation_length = oxygen_saturation.Length;
                            int oxygen_saturation_length1 = oxygen_saturation_length + 1;
                            string finalNewList1 = patientHealthInfo.Remove(0, oxygen_saturation_length1);
                            oxygen.Text = oxygen_saturation;
                            oxygenSaturationText.Text = oxygen_saturation + "   SO2";

                            patientHealthInfo = finalNewList1;

                            string body_temperature = patientHealthInfo.Substring(0, patientHealthInfo.IndexOf("`"));
                            int body_temperature_length = body_temperature.Length;
                            int body_temperature_length1 = body_temperature_length + 1;
                            string finalNewList2 = patientHealthInfo.Remove(0, body_temperature_length1);
                            bodyTemperature.Text = body_temperature;
                            bodyTemperatureText.Text = body_temperature + "   °C";

                            patientHealthInfo = finalNewList2;

                            string blood_pressure = patientHealthInfo.Substring(0, patientHealthInfo.IndexOf("`"));
                            int blood_pressure_length = blood_pressure.Length;
                            int blood_pressure_length1 = blood_pressure_length + 1;
                            string finalNewList3 = patientHealthInfo.Remove(0, blood_pressure_length1);
                            bloodPressure.Text = blood_pressure;
                            bloodPressureText.Text = blood_pressure + "   mm Hg";

                            patientHealthInfo = finalNewList3;

                            string respiratory_rate = patientHealthInfo.Substring(0, patientHealthInfo.IndexOf("`"));
                            int respiratory_rate_length = respiratory_rate.Length;
                            int respiratory_rate_length1 = respiratory_rate_length + 1;
                            string finalNewList4 = patientHealthInfo.Remove(0, respiratory_rate_length1);
                            respiratoryRate.Text = respiratory_rate;
                            respiratoryText.Text = respiratory_rate + "   Bpm";

                            patientHealthInfo = finalNewList4;

                            string temperature = patientHealthInfo.Substring(0, patientHealthInfo.IndexOf("`"));
                            int temperature_length = temperature.Length;
                            int temperature_length1 = temperature_length + 1;
                            string finalNewList5 = patientHealthInfo.Remove(0, temperature_length1);
                            roomTemperature.Text = temperature;
                            temperatureText.Text = temperature + "   °C";

                            patientHealthInfo = finalNewList5;

                            string humidity = patientHealthInfo.Substring(0, patientHealthInfo.IndexOf("`"));
                            int humidity_length = humidity.Length;
                            int humidity_length1 = humidity_length + 1;
                            string finalNewList6 = patientHealthInfo.Remove(0, humidity_length1);
                            humidityRoom.Text = humidity;
                            humidityText.Text = humidity + "   %";

                            patientHealthInfo = finalNewList6;

                            airQuality.Text = patientHealthInfo;
                            airQualityText.Text = patientHealthInfo + "   PPM";

                            patientHealthInfo = "";
                        }
                    }
                }

                MessageBox.Show("Patient Health Information Updated Successfully");

                pulserate.Visibility = Visibility.Hidden;
                oxygen.Visibility = Visibility.Hidden;
                bodyTemperature.Visibility = Visibility.Hidden;
                bloodPressure.Visibility = Visibility.Hidden;
                respiratoryRate.Visibility = Visibility.Hidden;
                roomTemperature.Visibility = Visibility.Hidden;
                humidityRoom.Visibility = Visibility.Hidden;
                airQuality.Visibility = Visibility.Hidden;


                pulseRateText.Visibility = Visibility.Visible;
                oxygenSaturationText.Visibility = Visibility.Visible;
                bodyTemperatureText.Visibility = Visibility.Visible;
                bloodPressureText.Visibility = Visibility.Visible;
                respiratoryText.Visibility = Visibility.Visible;
                temperatureText.Visibility = Visibility.Visible;
                humidityText.Visibility = Visibility.Visible;
                airQualityText.Visibility = Visibility.Visible;

                editPatientInfo.Visibility = Visibility.Visible;
                saveInfo.Visibility = Visibility.Hidden;
                cancel.Visibility = Visibility.Hidden;
            }

                else
                {
                    MessageBox.Show("Failure to update patient health information.", "Update Error", MessageBoxButton.OK, MessageBoxImage.Error);
                }
                
        }

        private void cancel_Click(object sender, RoutedEventArgs e)
        {
            pulserate.Visibility = Visibility.Hidden;
            oxygen.Visibility = Visibility.Hidden;
            bodyTemperature.Visibility = Visibility.Hidden;
            bloodPressure.Visibility = Visibility.Hidden;
            respiratoryRate.Visibility = Visibility.Hidden;
            roomTemperature.Visibility = Visibility.Hidden;
            humidityRoom.Visibility = Visibility.Hidden;
            airQuality.Visibility = Visibility.Hidden;


            pulseRateText.Visibility = Visibility.Visible;
            oxygenSaturationText.Visibility = Visibility.Visible;
            bodyTemperatureText.Visibility = Visibility.Visible;
            bloodPressureText.Visibility = Visibility.Visible;
            respiratoryText.Visibility = Visibility.Visible;
            temperatureText.Visibility = Visibility.Visible;
            humidityText.Visibility = Visibility.Visible;
            airQualityText.Visibility = Visibility.Visible;

            editPatientInfo.Visibility = Visibility.Visible;
            saveInfo.Visibility = Visibility.Hidden;
            cancel.Visibility = Visibility.Hidden;
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
    }
}
