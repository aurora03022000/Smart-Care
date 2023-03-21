<?php
if(isset($_POST['selectedValue']) && isset($_POST['searchValue'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $selectedValue = validate($_POST['selectedValue']);
    $searchValue = validate($_POST['searchValue']);

    
    if($selectedValue == "Room Number"){
        $sql1 = "SELECT * FROM room WHERE room_number='$searchValue' and isExist=1 ORDER BY room_number ASC";
        $result1 = $conn->query($sql1); 
    }
    
    else if($selectedValue == "Status"){
        $sql1 = "SELECT * FROM room WHERE room_status='$searchValue' and isExist=1 ORDER BY room_number ASC";
        $result1 = $conn->query($sql1); 
    }

    else if($selectedValue == "Current User Name"){
        $sql1 = "SELECT * FROM patient WHERE patient_name LIKE '%$searchValue%' and isExist=1";
        $result1 = $conn->query($sql1);
 
    }

    if($result1){
        if($selectedValue == "Current User Name"){
            while($row = mysqli_fetch_assoc($result1)){
                $sql2 = "SELECT * FROM room WHERE room_number = '$row[room]' and isExist=1";
                $result2 = $conn->query($sql2); 

                if($result2){
                    while($row1 = mysqli_fetch_assoc($result2)){
                        echo $row1['room_number'].'`'.$row1['room_status'].'`'.$row['patient_name'].'`';
                    }
                }

                else{
                    echo 'failure';
                }
            }
        }

        else{
            while($row = mysqli_fetch_assoc($result1)){
                $sql2 = "SELECT * FROM patient WHERE room = '$row[room_number]' and isExist=1";
                $result2 = $conn->query($sql2); 
                $result2Count = mysqli_num_rows($result2);

                if($result2Count > 0){
                    while($row2 = mysqli_fetch_assoc($result2)){
                        echo $row['room_number'].'`'.$row['room_status'].'`'.$row2['patient_name'].'`';
                    }
                }

                else if($result2Count == 0){
                    echo $row['room_number'].'`'.$row['room_status'].'`'."-".'`';
                }

                else{
                    echo 'failure';
                }
            }
        }
    }

    else{
        echo 'failure';
    }
    
}
?>