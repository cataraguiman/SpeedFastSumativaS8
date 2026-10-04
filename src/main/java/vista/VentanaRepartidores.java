package vista;
import controlador.RepartidorController;
import modelo.Repartidor;
import dao.implement.RepartidorDAOImpl;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

// VENTANA 1: REGISTRAR Y ADMINISTRAR REPARTIDORES.
public class VentanaRepartidores extends JFrame {

    private JTextField txtNombre;
    private JButton btnRegistrar;
    private JButton btnActualizar;
    private JButton btnEliminar;

    private JTable tablaRepartidores;
    private DefaultTableModel modeloTabla;

    private final RepartidorController repartidorController;

    public VentanaRepartidores() {

        // Conectamos la ventana con el Controller.
        repartidorController = new RepartidorController(
                new RepartidorDAOImpl()
        );

        // Configuración de la ventana.
        setTitle("Gestión de Repartidores");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Creamos el panel principal.
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        // Creamos el formulario.
        JPanel panelFormulario = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel lblNombre = new JLabel("Nombre:");

        txtNombre = new JTextField(20);

        btnRegistrar = new BotonDegradado("Registrar");
        btnActualizar = new BotonDegradado("Actualizar");
        btnEliminar = new BotonDegradado("Eliminar");

        panelFormulario.add(lblNombre);
        panelFormulario.add(txtNombre);
        panelFormulario.add(btnRegistrar);
        panelFormulario.add(btnActualizar);
        panelFormulario.add(btnEliminar);

        // Creamos la tabla.
        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Nombre"},
                0
        ) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaRepartidores = new JTable(modeloTabla);

        // Agregamos los componentes al panel principal.
        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);
        panelPrincipal.add(
                new JScrollPane(tablaRepartidores),
                BorderLayout.CENTER
        );

        // Agregamos el panel principal a la ventana.
        add(panelPrincipal);

        // Eventos de los botones.
        btnRegistrar.addActionListener(e -> registrarRepartidor());
        btnActualizar.addActionListener(e -> actualizarRepartidor());
        btnEliminar.addActionListener(e -> eliminarRepartidor());

        // Evento al seleccionar un repartidor de la tabla.
        tablaRepartidores.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int fila = tablaRepartidores.getSelectedRow();

                if (fila >= 0) {
                    txtNombre.setText(
                            tablaRepartidores.getValueAt(fila, 1).toString()
                    );
                }
            }
        });

        // Cargamos los repartidores al abrir la ventana.
        cargarRepartidores();
    }

    // Registra un nuevo repartidor.
    private void registrarRepartidor() {

        String nombre = txtNombre.getText().trim();

        // Validamos que el nombre no esté vacío.
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar el nombre del repartidor.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
            txtNombre.requestFocus();
            return;
        }

        try {

            repartidorController.crear(nombre);

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor registrado correctamente."
            );

            limpiarFormulario();
            cargarRepartidores();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar el repartidor:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Actualiza el repartidor seleccionado.
    private void actualizarRepartidor() {

        int fila = tablaRepartidores.getSelectedRow();

        // Validamos que exista una fila seleccionada.
        if (fila == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un repartidor de la tabla.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String nombre = txtNombre.getText().trim();

        // Validamos el nombre.
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar el nombre del repartidor.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
            txtNombre.requestFocus();
            return;
        }

        int id = Integer.parseInt(
                tablaRepartidores.getValueAt(fila, 0).toString()
        );

        try {

            repartidorController.actualizar(id, nombre);

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor actualizado correctamente."
            );

            limpiarFormulario();
            cargarRepartidores();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo actualizar el repartidor:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Elimina el repartidor seleccionado.
    private void eliminarRepartidor() {

        int fila = tablaRepartidores.getSelectedRow();

        // Validamos que exista una fila seleccionada.
        if (fila == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un repartidor de la tabla.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int id = Integer.parseInt(
                tablaRepartidores.getValueAt(fila, 0).toString()
        );

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar este repartidor?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            repartidorController.eliminar(id);

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor eliminado correctamente."
            );

            limpiarFormulario();
            cargarRepartidores();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar el repartidor:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Carga los repartidores desde la BD en la tabla.
    private void cargarRepartidores() {

        try {

            List<Repartidor> repartidores =
                    repartidorController.listar();

            modeloTabla.setRowCount(0);

            for (Repartidor repartidor : repartidores) {

                modeloTabla.addRow(
                        new Object[]{
                                repartidor.getId(),
                                repartidor.getNombre()
                        }
                );
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudieron cargar los repartidores:\n"
                            + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    // Limpia los datos del formulario.
    private void limpiarFormulario() {
        txtNombre.setText("");
        tablaRepartidores.clearSelection();
        txtNombre.requestFocus();
    }
}