   M S B A M L   `   `   `  ����E  ASmart Care, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null'clr-namespace:Smart_Care
Smart_Care  5   	       Smart_Care.hospitalList   5   	   \ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    5   	   R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   @d2http://schemas.microsoft.com/expression/blend/2008     5   	   Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5   	   *localclr-namespace:Smart_Care     5   	     � Title$  hospitalList��5   	   $��613.243��6   $��519.579��6/    � 
ResizeMode$ NoResize=�6?    � WindowStartupLocation$ CenterScreen=�6U   .��5	      � .+�5
   
   �  � RowDefinitions 5      �� 5      $	��97* �6    6/   �� 5      $
��323* �6    60   .+�5      � $��0��6   $��0,0,-0.4,1.8q�6    .+�5      ��  �� CornerRadius$ 0, 0, 10, 10��6   $��	0,0,0.4,9q�65   ��5      �� 5       j� Stretch$ UniformToFill=�6%    �� ImageSource$ backgroundLogin.jpg��6=   ._�5      � $��0��6   $��0,0,0,1q�6(   .+�5      �� $ 0, 0, 10, 10��6!   ���#0EB021 J� Opacity$	 0.9Q�6R   $��0,0,0,-2q�6`   5      �� 5      $��	staff.png��6    $��Center=�63   $��Center=�6P   $��44��6k   $��57��6v   $��	103,7,0,0q�6�   �� 5      $��18(�6    
�������$��Hospital List��6A   $��Center=�6Y   $��Center=�6t   $��Century Gothic)�6�   $
��Bold"�6�   $	��116��6�   $��32��6�   $��
-46,17,0,0q�6�   5      5      5      � 5       $��1��6   $��0,3.2,-0.4,0q�6    .+�5!      �� $��Hospital Name��6   $��Italic$�60   $��Century Gothic)�6C   $��10(�6_   $	��Top=�6m   $
��Left=�6�   $��	23,10,0,0q�6�   � 5"      
�������$��1��6-   $	��Top=�68   $��	26,29,0,0q�6P   $
��Left=�6c   $	��463��6~   �� 5#      -   5         ��Name$ hospitalNameTextBox��5#   /   $y�Visible=�6   $	��331��6L   $��31��6X   $	��Top=�6d   $
��Left=�6|   $��	26,36,0,0q�6�   $��Century Gothic)�6�   $��5,7,0,0q�6�   �� 5$      -   5        	 ��Name$	 addHospital��5$   '   
 � Cursor$

 Hand��6   $	��Top=�6<   $��31��6T   4  .System.Windows.Automation.AutomationProperties   Name$ registerStaff��6`   $��Century Gothic)�6�   $
��Left=�6�   $	��127��6�   $��
362,36,0,0q�6�    � 	Resources 5%       �� 
TargetType �� Property �� Value��%d   '��       �� 5&      " ��6    .�5'      ��  �@��CornerRadius$ 7,7,7,7��6=   5(      5)      .��5*      ]� .+�5+      �� $��operating-room.png��6    $��21��6<   $��34��6H   �� 5,      $
��Bold"�6$   $��Add Hospital��66   $��Center=�6J   $��Center=�6g   5-      5.      &   System.Windows.Controls.DataGrid  5/      -   5          Name$ hospitalDataGrid��5/   �   
�������   HeadersVisibility$ Column=�6.      CanUserResizeColumns .     CanUserReorderColumns .     CanUserResizeRows .     CanUserSortColumns .     AutoGenerateColumns .  $
��Left=�6�   $	��347��6  $��	26,72,0,0q�6  $	��Top=�6+  $	��463��6C     ColumnHeaderStyle 50      �� 51      " ��6    .�52      ��  � ��$ Century Gothic)�6;   �� 53       � ��
 �����6;   �� 54       � ��
 �����6;   �� 55       � ��$ 10, 5, 5, 5q�68   �� 56       � ��
 �   �6<   �� 57       � ��$ 1,1,1,1q�6@   58         	CellStyle 5:      �� 5;      *  $System.Windows.Controls.DataGridCell"  6    .�5<      ��  � ��$ 10, 5, 5, 2q�68   �� 5=       � �� 5>   "   �� 5?   &    �� 
TargetType"  66   .R�5@   *   ��  �� Padding# z��61   #��z��6U   #��z��6�   #��z��6�    J� SnapsToDevicePixels . ._�5A   .   �� # z 6?   5B   +   5C   '   5E      5F         Columns 5H      0  *System.Windows.Controls.DataGridTextColumn  5I         
FontFamily$ Century Gothic)�6-   ,  &System.Windows.Controls.DataGridColumn   
IsReadOnly .    CanUserSort .  1  +System.Windows.Controls.DataGridBoundColumn    Binding  6o   �� *hospital_id+  5J      $ Century Gothic)�6-    . !   Header$! Hospital name��6[    .  "   Width5  /System.Windows.Controls.DataGridLengthConverter$" * 6�   #   CanUserResize# .    6�   �� *hospital_name+4  .System.Windows.Controls.DataGridTemplateColumn  5K       . $! Delete��6C    .  $
" AUto 6g   # .  $   CellTemplate$ 5L      �� 5M   "   .R�5N   &   �� $	 btnDeleteHospital��6I   -   5        $��Century Gothic)�5N   -   $

 Hand��6d   .��5O   *   �� $��
delete.png��60   $��18��6D   $��19��6P   5P   '   5Q   #   5S      5U      5V      5W      5X      5Y      