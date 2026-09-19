## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

## How To Run

### Step 1: Go to the root of the project
```bash
cd path\to\Source Code
```

### Step 2: Create bin folder
```bash
mkdir bin
```

### Step 3: Compile all .java files
```bash
javac -d bin src\*.java src\data\*.java src\engine\*.java src\graphics\*.java
```

### Step 4: Copy only non-Java files from data and copy assets folder
```bash
xcopy src\data bin\data /E /I
del bin\data\*.java
 
xcopy src\assets bin\assets /E /I
```

### Step 5: Run the application from project root (HangOSman-main)
```bash
java -cp bin App
```
