<?php
if(isset($_POST['selectedValue']) && isset($_POST['searchValue'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $selectedValue = validate($_POST['selectedValue']);
    $searchValue = validate($_POST['searchValue']);

    
    if($selectedValue == "Medical Staff Name"){
        $sql1 = "SELECT * FROM user WHERE staff_name LIKE '%$searchValue%' ORDER BY staff_name ASC";
        $result1 = $conn->query($sql1); 
    }
    
    else if($selectedValue == "Username"){
        $sql1 = "SELECT * FROM user WHERE username LIKE '%$searchValue%' ORDER BY staff_name ASC";
        $result1 = $conn->query($sql1); 
    }

    else if($selectedValue == "Gender"){
        $sql1 = "SELECT * FROM user WHERE gender = '$searchValue' ORDER BY staff_name ASC";
        $result1 = $conn->query($sql1); 
    }

    else if($selectedValue == "Birthdate"){
        $birthdate = new DateTime($searchValue);
        $birthdateFinal = $birthdate->format('F d, Y');

        $sql1 = "SELECT * FROM user WHERE birthdate = '$birthdateFinal' ORDER BY staff_name ASC";
        $result1 = $conn->query($sql1); 
    }

    else if($selectedValue == "Number"){
        $sql1 = "SELECT * FROM user WHERE number = '$searchValue' ORDER BY staff_name ASC";
        $result1 = $conn->query($sql1); 
    }
    
    else if($selectedValue == "Address"){
        $sql1 = "SELECT * FROM user WHERE address LIKE '%$searchValue%' ORDER BY staff_name ASC";
        $result1 = $conn->query($sql1); 
    }

    else if($selectedValue == "Date Registered"){
        $dateRegistered = new DateTime($searchValue);
        $dateRegisteredFinal = $dateRegistered->format('F d, Y');
        $sql1 = "SELECT * FROM user WHERE date_registered = '$dateRegisteredFinal' ORDER BY staff_name ASC";
        $result1 = $conn->query($sql1); 
    }

    if($result1){
        
        if($selectedValue == "Birthdate"){
            while($row = mysqli_fetch_assoc($result1)){
                echo $row['user_id'].'`'.$row['staff_name'].'`'.$row['username'].'`'.$row['birthdate'].'`'.$row['gender'].'`'.$row['address'].'`';
            }
        }

        else if($selectedValue == "Number"){
            while($row = mysqli_fetch_assoc($result1)){
                echo $row['user_id'].'`'.$row['staff_name'].'`'.$row['username'].'`'.$row['number'].'`'.$row['gender'].'`'.$row['address'].'`';
            }
        }

        else if($selectedValue == "Date Registered"){
            while($row = mysqli_fetch_assoc($result1)){
                echo $row['user_id'].'`'.$row['staff_name'].'`'.$row['username'].'`'.$row['date_registered'].'`'.$row['gender'].'`'.$row['address'].'`';
            }
        }

        else{
            while($row = mysqli_fetch_assoc($result1)){
                echo $row['user_id'].'`'.$row['staff_name'].'`'.$row['username'].'`'.$row['birthdate'].'`'.$row['gender'].'`'.$row['address'].'`';
            }
        }
    }

    else{
        echo 'failure';
    }
    
}
?>