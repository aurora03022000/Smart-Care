<?php

require_once "conn.php";
require_once "validate.php";
// Establish connection to MySQL database

// If values send by NodeMCU are not empty then insert into MySQL database table

  if(!empty($_POST['raw_value']))
    {
        	$raw_value = $_POST['raw_value'];

			$stringOxygen = strstr($raw_value, 'oxygen:');

			$newStringWithoutOxygen = str_replace($stringOxygen,"", $raw_value);


			$stringPulse = strstr($newStringWithoutOxygen, 'pulse:');

			$newStringWithoutPulse = str_replace($stringPulse,"", $newStringWithoutOxygen);


			$stringIP = strstr($newStringWithoutPulse, 'ip:');

			$newStringWithoutIP = str_replace($stringIP,"", $newStringWithoutPulse);


			$stringDeviceID = strstr($newStringWithoutIP, 'device:');

			$newStringWithoutDevice = str_replace($stringDeviceID,"", $newStringWithoutIP);


			$stringCelcius = strstr($newStringWithoutDevice, 'temperature:');

			$newStringWithoutCelcius = str_replace($stringCelcius,"", $newStringWithoutDevice);


			$stringHumidity = strstr($newStringWithoutCelcius, 'humidity:');

			$newStringWithoutHumidity = str_replace($stringHumidity,"", $newStringWithoutCelcius);


			$stringBody = strstr($newStringWithoutHumidity, 'body:');

			$newStringWithoutBody = str_replace($stringBody,"", $newStringWithoutHumidity);

			$stringAir = strstr($newStringWithoutBody, 'air:');

			$newStringWithoutAir = str_replace($stringAir,"", $newStringWithoutBody);


			$stringEmergency = $newStringWithoutAir;

			$oxygenFinal = str_replace("oxygen:","", $stringOxygen);
			$pulseFinal = str_replace("pulse:","", $stringPulse);
			$deviceIDFinal = str_replace("device:","", $stringDeviceID);
			$airQualityFinal = str_replace("air:","", $stringAir);
			$humidityFinal = str_replace("humidity:","", $stringHumidity);
			$celciusFinal = str_replace("temperature:","", $stringCelcius);
			$bodyFinal = str_replace("body:","", $stringBody);
			$ipFinal = str_replace("ip:","", $stringIP);
			$emergency = str_replace("emergency:","", $stringEmergency);

			$selectDeviceID = "SELECT * FROM rawdata WHERE raw_data_device_id = '$deviceIDFinal'";
			$selectDeviceIDResult = mysqli_query($conn, $selectDeviceID);
			$selectDeviceIDCount = mysqli_num_rows($selectDeviceIDResult);

			// if($selectDeviceIDCount > 0){
			// 	$rawDataInsert = "UPDATE `rawdata` SET `raw_value` = '$raw_value' WHERE `raw_data_device_id` = '$deviceIDFinal'"; 
			// }

			// else if($selectDeviceIDCount == 0){
			// 	$rawDataInsert = "INSERT INTO `rawdata`(`raw_value`,`raw_data_device_id`) VALUES('$raw_value', '$deviceIDFinal')"; 
			// }
			$rawDataInsert = "UPDATE `rawdata` SET `raw_value` = '$raw_value'";

// Update your tablename here	    
		
		if ($conn->query($rawDataInsert) === TRUE) {

			

			$selectPatientWithDeviceID = "SELECT * FROM patient WHERE deviceID = '$deviceIDFinal' and isExist = 1";
			$selectPatientWithDeviceIDResult = mysqli_query($conn, $selectPatientWithDeviceID);

			if($selectPatientWithDeviceID){
				while($row = mysqli_fetch_assoc($selectPatientWithDeviceIDResult)){
					$oxygenFinalInt = (int) $oxygenFinal;
					$pulseFinalInt = (int) $pulseFinal;
					$airQualityFinal1 = (float) $airQualityFinal;
					$humidityFinal1 = (float) $humidityFinal;
					$celciusFinal1 = (float) $celciusFinal;
					$bodyFinalInt = (float) $bodyFinal;

					$booltest = is_float($celciusFinal1);
					$booltest1 = is_float($airQualityFinal1);
					$booltest2 = is_float($humidityFinal1);

					if($booltest && $booltest1 && $booltest2){
						
						$updatePatientHealthRecords = "UPDATE `patient_health_log` SET `body_temperature` = '$bodyFinalInt', `pulse_rate` = '$pulseFinalInt', `oxygen_saturation` = '$oxygenFinalInt', `air_quality` = '$airQualityFinal', `humidity` = '$humidityFinal', `room_temperature` = '$celciusFinal' WHERE `patient_id` = '$row[patient_id]' and isExist = 1"; 
						$updatePatientHealthRecordsResult = mysqli_query($conn, $updatePatientHealthRecords);
						
						if($updatePatientHealthRecordsResult){
	
							$setDeviceIDIP = "UPDATE deviceid SET ipAddress = '$ipFinal' WHERE device_id = '$deviceIDFinal'";
							$setDeviceIDIPResult = mysqli_query($conn, $setDeviceIDIP);
	
							if($setDeviceIDIPResult){
								if($emergency == "1"){
									$selectEncoder = "SELECT * FROM user WHERE user_id = '$row[encoder]'";
									$selectEncoderResult = mysqli_query($conn, $selectEncoder);

									if($selectEncoderResult){
										while($row1 = mysqli_fetch_assoc($selectEncoderResult)){
											if($row['share_id'] != ""){
												$selectEncoderShared = "SELECT * FROM user WHERE user_id = '$row[share_id]'";
												$selectEncoderSharedResult = mysqli_query($conn, $selectEncoderShared);

												if($selectEncoderSharedResult){
													while($row2 = mysqli_fetch_assoc($selectEncoderSharedResult)){
														echo $row2['number'];
													}
												}


												else{
													echo "Error: " . $sql . "<br>" . $conn->error; 	
												}

											}

											else{
												echo $row1['number'];
											}
										}
									}

									else{
										echo "Error: " . $sql . "<br>" . $conn->error; 	
									}
								}

								else{
									echo "Updated Successfully"; 
								}
							}
	
							else{
								echo "Error: " . $sql . "<br>" . $conn->error; 	
							}
							
						}
			
						else{
							echo "Error: " . $sql . "<br>" . $conn->error; 
						}
					}

					else{
						echo "Error";
					}
				}
			}

			else{
				echo "Error: " . $sql . "<br>" . $conn->error; 
			}

			
		}
		
		else {
		    echo "Error: " . $sql . "<br>" . $conn->error;
		}
	}


// Close MySQL connection
$conn->close();



?>