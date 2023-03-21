<?php
if(isset($_POST['selectedValue']) && isset($_POST['searchValue'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $selectedValue = validate($_POST['selectedValue']);
    $searchValue = validate($_POST['searchValue']);

    
    if($selectedValue == "Patient Name"){
        $sql1 = "SELECT * FROM patient WHERE patient_name LIKE '%$searchValue%' ORDER BY `patient_name` ASC";
        $result1 = $conn->query($sql1); 
    }
    
    else if($selectedValue == "Gender"){
        $sql1 = "SELECT * FROM patient WHERE gender='$searchValue' ORDER BY `patient_name` ASC";
        $result1 = $conn->query($sql1); 
    }
    else if($selectedValue == "Birthdate"){
        $birthdate = new DateTime($searchValue);
        $birthdateFinal = $birthdate->format('F d, Y');

        $sql1 = "SELECT * FROM patient WHERE birthdate = '$birthdateFinal' ORDER BY `patient_name` ASC";
        $result1 = $conn->query($sql1); 
    }

    else if($selectedValue == "Number"){
        $sql1 = "SELECT * FROM patient WHERE number = '$searchValue' ORDER BY `patient_name` ASC";
        $result1 = $conn->query($sql1); 
    }
    
    else if($selectedValue == "Address"){
        $sql1 = "SELECT * FROM patient WHERE address LIKE '%$searchValue%' ORDER BY `patient_name` ASC";
        $result1 = $conn->query($sql1); 
    }

    else if($selectedValue == "Disease"){
        $sql1 = "SELECT * FROM patient WHERE disease LIKE '%$searchValue%' ORDER BY `patient_name` ASC";
        $result1 = $conn->query($sql1); 
    }

    else if($selectedValue == "Device ID"){
        $sql1 = "SELECT * FROM patient WHERE deviceID LIKE '%$searchValue%' ORDER BY `patient_name` ASC";
        $result1 = $conn->query($sql1); 
    }

    else if($selectedValue == "Room"){
        $sql1 = "SELECT * FROM patient WHERE room LIKE '%$searchValue%' ORDER BY `patient_name` ASC";
        $result1 = $conn->query($sql1); 
    }
    
    else if($selectedValue == "Encoder Name"){
        $sql1 = "SELECT * FROM user WHERE staff_name LIKE '%$searchValue%'";
        $result1 = $conn->query($sql1); 
    }

    else if($selectedValue == "Date Admitted"){
        $dateRegistered = new DateTime($searchValue);
        $dateRegisteredFinal = $dateRegistered->format('F d, Y');
        $sql1 = "SELECT * FROM patient WHERE date_admit = '$dateRegisteredFinal' ORDER BY `patient_name` ASC";
        $result1 = $conn->query($sql1); 
    }

    else if($selectedValue == "Date Dismissed"){
        $dateRegistered = new DateTime($searchValue);
        $dateRegisteredFinal = $dateRegistered->format('F d, Y');
        $sql1 = "SELECT * FROM patient WHERE date_dismiss = '$dateRegisteredFinal' ORDER BY `patient_name` ASC";
        $result1 = $conn->query($sql1); 
    }

    if($result1){
        
        if($selectedValue == "Birthdate"){
            while($row = mysqli_fetch_assoc($result1)){
                echo $row['patient_id'].'`'.$row['patient_name'].'`'.$row['gender'].'`'.$row['birthdate'].'`'.$row['date_admit'].'`'.$row['date_dismiss'].'`';
            }
        }

        else if($selectedValue == "Number"){
            while($row = mysqli_fetch_assoc($result1)){
                echo $row['patient_id'].'`'.$row['patient_name'].'`'.$row['gender'].'`'.$row['number'].'`'.$row['date_admit'].'`'.$row['date_dismiss'].'`';
            }
        }

        else if($selectedValue == "Disease"){
            while($row = mysqli_fetch_assoc($result1)){
                echo $row['patient_id'].'`'.$row['patient_name'].'`'.$row['gender'].'`'.$row['disease'].'`'.$row['date_admit'].'`'.$row['date_dismiss'].'`';
            }
        }

        else if($selectedValue == "Device ID"){
            while($row = mysqli_fetch_assoc($result1)){
                echo $row['patient_id'].'`'.$row['patient_name'].'`'.$row['gender'].'`'.$row['deviceID'].'`'.$row['date_admit'].'`'.$row['date_dismiss'].'`';
            }
        }

        else if($selectedValue == "Room"){
            while($row = mysqli_fetch_assoc($result1)){
                echo $row['patient_id'].'`'.$row['patient_name'].'`'.$row['gender'].'`'.$row['room'].'`'.$row['date_admit'].'`'.$row['date_dismiss'].'`';
            }
        }

        else if($selectedValue == "Encoder Name"){
            while($row = mysqli_fetch_assoc($result1)){
                $sql2 = "SELECT * FROM patient WHERE encoder='$row[user_id]' ORDER BY `patient_name` ASC";
                $result2 = $conn->query($sql2);  

                if($result2){
                    while($row1 = mysqli_fetch_assoc($result2)){
                        echo $row1['patient_id'].'`'.$row1['patient_name'].'`'.$row1['gender'].'`'.$row['staff_name'].'`'.$row1['date_admit'].'`'.$row1['date_dismiss'].'`';
                    }
                }

                else{
                    echo 'failure';
                }
            }
        }

        else{
            while($row = mysqli_fetch_assoc($result1)){
                echo $row['patient_id'].'`'.$row['patient_name'].'`'.$row['gender'].'`'.$row['address'].'`'.$row['date_admit'].'`'.$row['date_dismiss'].'`';
            }
        }
    }

    else{
        echo 'failure';
    }
    
}
?>