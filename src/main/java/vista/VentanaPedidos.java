package vista;

import controlador.PedidoController;
import dao.implement.PedidoDAOImpl;
import modelo.Pedido;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

// VENTANA 2: REGISTRAR Y ADMINISTRAR PEDIDOS.
public class VentanaPedidos extends JFrame {

    private JTextField txtDireccion;

    private JComboBox<String> cmbTipo;
    private JComboBox<String> cmbEstado;
    private JComboBox<String> cmbFiltroTipo;
    private JComboBox<String> cmbFiltroEstado;

    private JButton btnRegistrar;
    private JButton btnActualizar;
    private JButton btnEliminar;

    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;

    private final PedidoController pedidoController;

    public VentanaPedidos() {

        // Conectamos la ventana con el Controller.
        pedidoController = new PedidoController(
                new PedidoDAOImpl()
        );

        // Configuración de la ventana.
        setTitle("Gestión de Pedidos");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal.
        JPanel panelPrincipal = new JPanel(
                new BorderLayout(10, 10)
        );

        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        // Panel del formulario.
        JPanel panelFormulario = new JPanel(
                new GridBagLayout()
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel lblDireccion = new JLabel("Dirección:");
        JLabel lblTipo = new JLabel("Tipo:");
        JLabel lblEstado = new JLabel("Estado:");

        txtDireccion = new JTextField();

        cmbTipo = new JComboBox<>(
                new String[]{
                        "Comida",
                        "Encomienda",
                        "Express"
                }
        );

        cmbEstado = new JComboBox<>(
                new String[]{
                        "Pendiente",
                        "En_Reparto",
                        "Entregado"
                }
        );

        btnRegistrar = new BotonDegradado("Registrar");
        btnActualizar = new BotonDegradado("Actualizar");
        btnEliminar = new BotonDegradado("Eliminar");

        // Dirección.
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panelFormulario.add(lblDireccion, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        panelFormulario.add(txtDireccion, gbc);

        // Tipo.
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panelFormulario.add(lblTipo, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 0.8;
        panelFormulario.add(cmbTipo, gbc);

        // Estado.
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelFormulario.add(lblEstado, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        panelFormulario.add(cmbEstado, gbc);

        // Botones.
        JPanel panelBotones = new JPanel(
                new GridLayout(1, 3, 8, 0)
        );

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        panelFormulario.add(panelBotones, gbc);

        // Panel de filtros.
        JPanel panelFiltros = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        10,
                        5
                )
        );

        JLabel lblFiltroTipo = new JLabel("Filtrar por tipo:");
        JLabel lblFiltroEstado = new JLabel("Filtrar por estado:");

        cmbFiltroTipo = new JComboBox<>(
                new String[]{
                        "Todos",
                        "Comida",
                        "Encomienda",
                        "Express"
                }
        );

        cmbFiltroEstado = new JComboBox<>(
                new String[]{
                        "Todos",
                        "Pendiente",
                        "En Reparto",
                        "Entregado"
                }
        );

        panelFiltros.add(lblFiltroTipo);
        panelFiltros.add(cmbFiltroTipo);
        panelFiltros.add(lblFiltroEstado);
        panelFiltros.add(cmbFiltroEstado);

        // Panel superior.
        JPanel panelSuperior = new JPanel(
                new BorderLayout(10, 5)
        );

        panelSuperior.add(
                panelFormulario,
                BorderLayout.NORTH
        );

        panelSuperior.add(
                panelFiltros,
                BorderLayout.SOUTH
        );

        // Creamos la tabla.
        modeloTabla = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Dirección",
                        "Tipo",
                        "Estado"
                },
                0
        ) {

            @Override
            public boolean isCellEditable(
                    int fila,
                    int columna
            ) {
                return false;
            }
        };

        tablaPedidos = new JTable(modeloTabla);

        tablaPedidos.setRowHeight(25);

        // Se agrega los componentes.
        panelPrincipal.add(
                panelSuperior,
                BorderLayout.NORTH
        );

        panelPrincipal.add(
                new JScrollPane(tablaPedidos),
                BorderLayout.CENTER
        );

        add(panelPrincipal);

        // Eventos de los botones.
        btnRegistrar.addActionListener(e -> registrarPedido());
        btnActualizar.addActionListener(e -> actualizarPedido());
        btnEliminar.addActionListener(e -> eliminarPedido());

        // Eventos de los filtros.
        cmbFiltroTipo.addActionListener(e -> aplicarFiltros());
        cmbFiltroEstado.addActionListener(e -> aplicarFiltros());

        // Evento al seleccionar un pedido.
        tablaPedidos.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int fila = tablaPedidos.getSelectedRow();

                if (fila >= 0) {

                    txtDireccion.setText(
                            tablaPedidos.getValueAt(fila, 1).toString()
                    );

                    cmbTipo.setSelectedItem(
                            tablaPedidos.getValueAt(fila, 2).toString()
                    );

                    cmbEstado.setSelectedItem(
                            tablaPedidos.getValueAt(fila, 3).toString()
                    );
                }
            }
        });

        // Se carga los pedidos al abrir la ventana.
        cargarPedidos();
    }

    // Registra un nuevo pedido.
    private void registrarPedido() {

        String direccion = txtDireccion.getText().trim();
        String tipo = cmbTipo.getSelectedItem().toString();
        String estado = cmbEstado.getSelectedItem().toString();

        // Valida la dirección.
        if (direccion.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar la dirección del pedido.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            txtDireccion.requestFocus();
            return;
        }

        try {

            pedidoController.crear(
                    direccion,
                    tipo,
                    estado
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido registrado correctamente."
            );

            limpiarFormulario();
            cargarPedidos();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar el pedido:\n"
                            + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Actualiza el pedido seleccionado.
    private void actualizarPedido() {

        int fila = tablaPedidos.getSelectedRow();

        // Valida que exista una fila seleccionada.
        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un pedido de la tabla para continuar.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String direccion = txtDireccion.getText().trim();
        String tipo = cmbTipo.getSelectedItem().toString();
        String estado = cmbEstado.getSelectedItem().toString();

        // Validamos la dirección.
        if (direccion.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese la dirección del pedido.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            txtDireccion.requestFocus();
            return;
        }

        int id = Integer.parseInt(
                tablaPedidos.getValueAt(fila, 0).toString()
        );

        try {

            pedidoController.actualizar(
                    id,
                    direccion,
                    tipo,
                    estado
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido actualizado exitosamente."
            );

            limpiarFormulario();
            cargarPedidos();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible guardar los cambios del pedido.\n"
                            + "Revise los datos e inténtelo nuevamente.",
                    "Actualización no realizada",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Elimina el pedido seleccionado.
    private void eliminarPedido() {

        int fila = tablaPedidos.getSelectedRow();

        // Validamos que exista una fila seleccionada.
        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un pedido de la tabla.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int id = Integer.parseInt(
                tablaPedidos.getValueAt(fila, 0).toString()
        );

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar este pedido?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            pedidoController.eliminar(id);

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido eliminado correctamente."
            );

            limpiarFormulario();
            cargarPedidos();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar el pedido:\n"
                            + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Carga los pedidos desde la BD.
    private void cargarPedidos() {

        try {

            List<Pedido> pedidos =
                    pedidoController.listar();

            modeloTabla.setRowCount(0);

            for (Pedido pedido : pedidos) {

                modeloTabla.addRow(
                        new Object[]{
                                pedido.getId(),
                                pedido.getDireccion(),
                                pedido.getTipo(),
                                pedido.getEstado()
                        }
                );
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudieron cargar los pedidos:\n"
                            + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Aplica los filtros seleccionados.
    private void aplicarFiltros() {

        String tipoSeleccionado =
                cmbFiltroTipo.getSelectedItem().toString();

        String estadoSeleccionado =
                cmbFiltroEstado.getSelectedItem().toString();

        try {

            List<Pedido> pedidos =
                    pedidoController.listar();

            modeloTabla.setRowCount(0);

            for (Pedido pedido : pedidos) {

                boolean coincideTipo =
                        tipoSeleccionado.equals("Todos")
                                || pedido.getTipo().equals(tipoSeleccionado);

                boolean coincideEstado =
                        estadoSeleccionado.equals("Todos")
                                || pedido.getEstado().equals(estadoSeleccionado);

                if (coincideTipo && coincideEstado) {

                    modeloTabla.addRow(
                            new Object[]{
                                    pedido.getId(),
                                    pedido.getDireccion(),
                                    pedido.getTipo(),
                                    pedido.getEstado()
                            }
                    );
                }
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudieron aplicar los filtros:\n"
                            + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Limpia los datos del formulario.
    private void limpiarFormulario() {

        txtDireccion.setText("");

        cmbTipo.setSelectedIndex(0);
        cmbEstado.setSelectedIndex(0);

        tablaPedidos.clearSelection();

        txtDireccion.requestFocus();
    }
}