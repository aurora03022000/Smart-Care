<?php
if(isset($_POST['selectedValue']) && isset($_POST['searchValue'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $selectedValue = validate($_POST['selectedValue']);
    $searchValue = validate($_POST['searchValue']);

    if($selectedValue == "Patient Name"){
        $sql1 = "SELECT * FROM patient WHERE patient_name LIKE '%$searchValue%' ORDER BY patient_name ASC";
        $result1 = $conn->query($sql1); 
    }

    else if($selectedValue == "Gender"){
        $sql1 = "SELECT * FROM patient WHERE gender = '$searchValue' ORDER BY patient_name ASC";
        $result1 = $conn->query($sql1); 
    }

    else if($selectedValue == "Birthdate"){
        $birthdate = new DateTime($searchValue);
        $birthdateFinal = $birthdate->format('F d, Y');
        $sql1 = "SELECT * FROM patient WHERE birthdate = '$birthdateFinal' ORDER BY patient_name ASC";
        $result1 = $conn->query($sql1); 
    }

    else if($selectedValue == "Number"){
        $sql1 = "SELECT * FROM patient WHERE number = '$searchValue' ORDER BY patient_name ASC";
        $result1 = $conn->query($sql1); 
    }
    
    else if($selectedValue == "Address"){
        $sql1 = "SELECT * FROM patient WHERE address LIKE '%$searchValue%' ORDER BY patient_name ASC";
        $result1 = $conn->query($sql1); 
    }

    else if($selectedValue == "Disease"){
        $sql1 = "SELECT * FROM patient WHERE disease LIKE '%$searchValue%' ORDER BY patient_name ASC";
        $result1 = $conn->query($sql1); 
    }

    else if($selectedValue == "Device ID"){
        $sql1 = "SELECT * FROM patient WHERE deviceID = '$searchValue' ORDER BY patient_name ASC";
        $result1 = $conn->query($sql1); 
    }

    else if($selectedValue == "Room"){
        $sql1 = "SELECT * FROM patient WHERE ROOM = '$searchValue' ORDER BY patient_name ASC";
        $result1 = $conn->query($sql1); 
    }

    else if($selectedValue == "Date Admitted"){
        $dateAdmitted = new DateTime($searchValue);
        $dateAdmittedFinal = $dateAdmitted->format('F d, Y');
        $sql1 = "SELECT * FROM patient WHERE date_admit = '$dateAdmittedFinal' ORDER BY patient_name ASC";
        $result1 = $conn->query($sql1); 
    }
    

    if($result1){
        
        if($selectedValue == "Birthdate"){
            while($row = mysqli_fetch_assoc($result1)){
                echo $row['patient_id'].'`'.$row['patient_name'].'`'.$row['gender'].'`'.$row['birthdate'].'`'.$row['address'].'`';
            }
        }

        else if($selectedValue == "Number"){
            while($row = mysqli_fetch_assoc($result1)){
                echo $row['patient_id'].'`'.$row['patient_name'].'`'.$row['gender'].'`'.$row['number'].'`'.$row['address'].'`';
            }
        }

        else if($selectedValue == "Date Admitted"){
            while($row = mysqli_fetch_assoc($result1)){
                echo $row['patient_id'].'`'.$row['patient_name'].'`'.$row['gender'].'`'.$row['date_admit'].'`'.$row['address'].'`';
            }
        }

        else if($selectedValue == "Device ID"){
            while($row = mysqli_fetch_assoc($result1)){
                echo $row['patient_id'].'`'.$row['patient_name'].'`'.$row['gender'].'`'.$row['deviceID'].'`'.$row['address'].'`';
            }
        }

        else if($selectedValue == "Room"){
            while($row = mysqli_fetch_assoc($result1)){
                echo $row['patient_id'].'`'.$row['patient_name'].'`'.$row['gender'].'`'.$row['room'].'`'.$row['address'].'`';
            }
        }

        else if($selectedValue == "Disease"){
            while($row = mysqli_fetch_assoc($result1)){
                echo $row['patient_id'].'`'.$row['patient_name'].'`'.$row['gender'].'`'.$row['disease'].'`'.$row['address'].'`';
            }
        }

        else{
            while($row = mysqli_fetch_assoc($result1)){
                echo $row['patient_id'].'`'.$row['patient_name'].'`'.$row['gender'].'`'.$row['birthdate'].'`'.$row['address'].'`';
            }
        }
    }

    else{
        echo 'failure';
    }
    
}
?>