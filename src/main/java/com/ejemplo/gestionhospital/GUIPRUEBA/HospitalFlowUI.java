package com.ejemplo.gestionhospital.GUIPRUEBA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class HospitalFlowUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            JFrame frame = new JFrame("🏥 HospitalFlow");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 500);
            frame.setLocationRelativeTo(null);

            CardLayout cardLayout = new CardLayout();
            JPanel mainPanel = new JPanel(cardLayout);

            LoginScreen login = new LoginScreen(mainPanel, cardLayout);
            MainScreen main = new MainScreen(mainPanel, cardLayout);
            RoomsScreen rooms = new RoomsScreen(mainPanel, cardLayout);
            PatientsScreen patients = new PatientsScreen(mainPanel, cardLayout);
            BedsScreen beds = new BedsScreen(mainPanel, cardLayout, rooms, patients);

            main.setPatientsScreen(patients);

            mainPanel.add(login, "login");
            mainPanel.add(main, "main");
            mainPanel.add(rooms, "rooms");
            mainPanel.add(beds, "beds");
            mainPanel.add(patients, "patients");

            frame.add(mainPanel);
            frame.setVisible(true);
        });
    }
}

class LoginScreen extends JPanel {
    public LoginScreen(JPanel mainPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        JLabel title = new JLabel("Bienvenido a HospitalFlow", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        formPanel.add(new JLabel("Usuario:"));
        JTextField userField = new JTextField();
        formPanel.add(userField);

        formPanel.add(new JLabel("Contraseña:"));
        JPasswordField passField = new JPasswordField();
        formPanel.add(passField);

        formPanel.add(new JLabel());
        JButton loginBtn = new JButton("Iniciar sesión");
        formPanel.add(loginBtn);

        add(formPanel, BorderLayout.CENTER);

        loginBtn.addActionListener(e -> cardLayout.show(mainPanel, "main"));
    }
}

class MainScreen extends JPanel {
    private PatientsScreen patientsScreen;

    public void setPatientsScreen(PatientsScreen screen) {
        this.patientsScreen = screen;
    }

    public MainScreen(JPanel mainPanel, CardLayout cardLayout) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(60, 100, 60, 100));

        JLabel title = new JLabel("Menú principal", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);

        add(Box.createRigidArea(new Dimension(0, 30)));

        JButton roomsBtn = new JButton("🏠 Habitaciones");
        JButton bedsBtn = new JButton("🛏️ Camas");
        JButton registerPatientBtn = new JButton("➕ Registrar Paciente");
        JButton showPatientsBtn = new JButton("📋 Mostrar Pacientes");

        for (JButton btn : new JButton[]{roomsBtn, bedsBtn, registerPatientBtn, showPatientsBtn}) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(250, 40));
            btn.setFont(new Font("SansSerif", Font.PLAIN, 16));
            add(btn);
            add(Box.createRigidArea(new Dimension(0, 20)));
        }

        roomsBtn.addActionListener(e -> cardLayout.show(mainPanel, "rooms"));
        bedsBtn.addActionListener(e -> cardLayout.show(mainPanel, "beds"));
        registerPatientBtn.addActionListener(e -> {
            JTextField nombre = new JTextField();
            JTextField apellidos = new JTextField();
            JTextField dni = new JTextField();
            String[] niveles = {"Baja", "Media", "Alta", "Crítica"};
            JComboBox<String> gravedad = new JComboBox<>(niveles);

            JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
            panel.add(new JLabel("Nombre:")); panel.add(nombre);
            panel.add(new JLabel("Apellidos:")); panel.add(apellidos);
            panel.add(new JLabel("DNI:")); panel.add(dni);
            panel.add(new JLabel("Gravedad:")); panel.add(gravedad);

            int result = JOptionPane.showConfirmDialog(this, panel, "Registrar nuevo paciente", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String info = "ID: " + UUID.randomUUID().toString().substring(0, 8) +
                        " | Nombre: " + nombre.getText() +
                        " | Apellidos: " + apellidos.getText() +
                        " | DNI: " + dni.getText() +
                        " | Gravedad: " + gravedad.getSelectedItem();
                patientsScreen.addPatient(info);
                JOptionPane.showMessageDialog(this, "Paciente registrado exitosamente.");
            }
        });

        showPatientsBtn.addActionListener(e -> cardLayout.show(mainPanel, "patients"));
    }
}

class RoomsScreen extends JPanel {
    private JTextArea area;
    private java.util.List<String> rooms;
    private Map<String, Integer> roomCapacities = new HashMap<>();

    public RoomsScreen(JPanel mainPanel, CardLayout cardLayout) {
        rooms = new ArrayList<>();
        rooms.add("Habitación 1 Capacidad: 5"); roomCapacities.put("Habitación 1", 5);
        rooms.add("Habitación 2 Capacidad: 3"); roomCapacities.put("Habitación 2", 3);
        rooms.add("Habitación 3 Capacidad: 2"); roomCapacities.put("Habitación 3", 2);

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        area = new JTextArea();
        area.setFont(new Font("Monospaced", Font.PLAIN, 16));
        area.setEditable(false);
        updateTextArea();
        add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backBtn = new JButton("⬅️ Atrás");
        JButton addRoomBtn = new JButton("➕ Añadir Habitación");
        bottomPanel.add(backBtn);
        bottomPanel.add(addRoomBtn);

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "main"));
        addRoomBtn.addActionListener(e -> showAddRoomDialog());

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void showAddRoomDialog() {
        String input = JOptionPane.showInputDialog(this, "¿Cuántas camas tiene la nueva habitación?", "Nueva Habitación", JOptionPane.QUESTION_MESSAGE);
        if (input != null && !input.isEmpty()) {
            try {
                int beds = Integer.parseInt(input);
                String room = "Habitación " + (rooms.size() + 1);
                rooms.add(room + " Capacidad: " + beds);
                roomCapacities.put(room, beds);
                updateTextArea();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor, introduce un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateTextArea() {
        area.setText(String.join("\n", rooms));
    }

    public java.util.List<String> getRoomNames() {
        java.util.List<String> names = new ArrayList<>();
        for (int i = 0; i < rooms.size(); i++) {
            names.add("Habitación " + (i + 1));
        }
        return names;
    }

    public Map<String, Integer> getRoomCapacities() {
        return roomCapacities;
    }
}

class BedsScreen extends JPanel {
    private JTextArea area;
    private Map<String, List<String>> bedsMap;
    private RoomsScreen roomsScreen;
    private PatientsScreen patientsScreen;

    public BedsScreen(JPanel mainPanel, CardLayout cardLayout, RoomsScreen roomsScreen, PatientsScreen patientsScreen) {
        this.roomsScreen = roomsScreen;
        this.patientsScreen = patientsScreen;
        bedsMap = new LinkedHashMap<>();
        for (String room : roomsScreen.getRoomNames()) {
            bedsMap.put(room, new ArrayList<>());
        }

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        area = new JTextArea();
        area.setFont(new Font("Monospaced", Font.PLAIN, 16));
        area.setEditable(false);
        updateTextArea();
        add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backBtn = new JButton("⬅️ Atrás");
        JButton addBedBtn = new JButton("➕ Añadir Cama");
        bottomPanel.add(backBtn);
        bottomPanel.add(addBedBtn);

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "main"));
        addBedBtn.addActionListener(e -> showAddBedDialog());

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void showAddBedDialog() {
        java.util.List<String> roomOptions = roomsScreen.getRoomNames();
        String room = (String) JOptionPane.showInputDialog(this, "Selecciona habitación:", "Añadir Cama", JOptionPane.QUESTION_MESSAGE, null, roomOptions.toArray(new String[0]), null);
        if (room != null) {
            Map<String, Integer> capacities = roomsScreen.getRoomCapacities();
            int capacity = capacities.getOrDefault(room, 0);
            int currentPatients = bedsMap.getOrDefault(room, new ArrayList<>()).size();

            if (currentPatients >= capacity) {
                JOptionPane.showMessageDialog(this, "No se pueden añadir más pacientes. Capacidad máxima alcanzada.", "Capacidad completa", JOptionPane.WARNING_MESSAGE);
                return;
            }

            List<String> unassigned = patientsScreen.getUnassignedPatients();
            if (unassigned.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay pacientes sin cama asignada.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String patient = (String) JOptionPane.showInputDialog(this, "Selecciona paciente:", "Asignar paciente a cama", JOptionPane.QUESTION_MESSAGE, null, unassigned.toArray(), null);
            if (patient != null && !patient.isEmpty()) {
                bedsMap.computeIfAbsent(room, k -> new ArrayList<>()).add(patient);
                patientsScreen.addAssignedPatient(patient);
                updateTextArea();
            }
        }
    }

    private void updateTextArea() {
        StringBuilder sb = new StringBuilder();
        for (String room : bedsMap.keySet()) {
            sb.append(room).append("\n");
            List<String> patients = bedsMap.get(room);
            if (patients.isEmpty()) {
                sb.append("  0 camas\n");
            } else {
                for (int i = 0; i < patients.size(); i++) {
                    sb.append("  Paciente ").append(i + 1).append(": ").append(patients.get(i)).append("\n");
                }
            }
            sb.append("\n");
        }
        area.setText(sb.toString());
    }
}


class PatientsScreen extends JPanel {
    private JTextArea area;
    private List<String> patients = new ArrayList<>();
    private Set<String> assignedPatients = new HashSet<>();

    public PatientsScreen(JPanel mainPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel("📋 Lista de Pacientes", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        area = new JTextArea();
        area.setFont(new Font("Monospaced", Font.PLAIN, 16));
        area.setEditable(false);
        add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backBtn = new JButton("⬅️ Atrás");
        bottomPanel.add(backBtn);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "main"));
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void addPatient(String patientInfo) {
        patients.add(patientInfo);
        area.append(patientInfo + "\n\n");
    }

    public List<String> getUnassignedPatients() {
        List<String> unassigned = new ArrayList<>();
        for (String p : patients) {
            if (!assignedPatients.contains(p)) {
                unassigned.add(p);
            }
        }
        return unassigned;
    }

    public void addAssignedPatient(String patient) {
        assignedPatients.add(patient);
    }
}