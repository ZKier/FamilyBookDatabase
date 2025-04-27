$JarPath = "C:\Users\zacki\Desktop\Family Book Program\FamilyBookDatabase\out\artifacts\FamilyBookDatabase_jar\FamilyBookDatabase.jar"
$ModulePath = "C:\Users\zacki\Downloads\openjfx-21.0.6_windows-x64_bin-sdk\javafx-sdk-21.0.6\lib"

# Build the full Java command
$JavaCmd = @(
"--module-path `"$ModulePath`"",
"--add-modules=javafx.controls,javafx.base,javafx.fxml,javafx.graphics,javafx.media,javafx.web",
"--add-opens=javafx.graphics/javafx.scene=ALL-UNNAMED",
"--add-exports=javafx.base/com.sun.javafx.event=ALL-UNNAMED",
"-jar `"$JarPath`""
) -join " "

# Run the command
Start-Process "java" $JavaCmd -NoNewWindow -Wait
Read-Host -Prompt "Press Enter to exit"