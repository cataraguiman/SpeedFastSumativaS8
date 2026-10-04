package vista;

import controlador.EntregaController;
import controlador.PedidoController;
import controlador.RepartidorController;
import dao.implement.EntregaDAOImpl;
import dao.implement.PedidoDAOImpl;
import dao.implement.RepartidorDAOImpl;
import modelo.Entrega;
import modelo.Pedido;
import modelo.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

// VENTANA 3: REGISTRAR Y ADMINISTRAR ENTREGAS.
public class VentanaEntregas extends JFrame {

    private JComboBox<Pedido> cmbPedido;
    private JComboBox<Repartidor> cmbRepartidor;
    private JTextField txtFecha;
    private JTextField txtHora;
    private JComboBox<Pedido> cmbFiltroPedido;
    private JComboBox<Repartidor> cmbFiltroRepartidor;
    private JButton btnRegistrar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JTable tablaEntregas;
    private DefaultTableModel modeloTabla;

    private final EntregaController entregaController;

    // Controllers: carga pedidos y repartidores.
    private final PedidoController pedidoController;
    private final RepartidorController repartidorController;

    // Validación fecha y hora.
    private final DateTimeFormatter formatoFecha =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final DateTimeFormatter formatoHora =
            DateTimeFormatter.ofPattern("HH:mm");

    public VentanaEntregas() {

        // Conexión de la ventana con los Controllers.
        entregaController = new EntregaController(
                new EntregaDAOImpl()
        );

        pedidoController = new PedidoController(
                new PedidoDAOImpl()
        );

        repartidorController = new RepartidorController(
                new RepartidorDAOImpl()
        );

        // Configuración de la ventana.
        setTitle("Gestión de Entregas");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // PANEL PRINCIPAL.
        JPanel panelPrincipal = new JPanel(
                new BorderLayout(15, 15)
        );

        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15
                )
        );

        // PANEL FORMULARIO.
        JPanel panelFormulario = new JPanel(
                new GridBagLayout()
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel lblPedido = new JLabel("Pedido:");
        JLabel lblRepartidor = new JLabel("Repartidor:");
        JLabel lblFecha = new JLabel("Fecha:");
        JLabel lblHora = new JLabel("Hora:");

        cmbPedido = new JComboBox<>();
        cmbRepartidor = new JComboBox<>();

        txtFecha = new JTextField();
        txtHora = new JTextField();

        btnRegistrar = new BotonDegradado("Registrar");
        btnActualizar = new BotonDegradado("Actualizar");
        btnEliminar = new BotonDegradado("Eliminar");

        // Pedido.
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panelFormulario.add(lblPedido, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.gridwidth = 1;
        panelFormulario.add(cmbPedido, gbc);

        // Repartidor.
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panelFormulario.add(lblRepartidor, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 1;
        panelFormulario.add(cmbRepartidor, gbc);

        // Fecha.
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelFormulario.add(lblFecha, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        panelFormulario.add(txtFecha, gbc);

        // Hora.
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelFormulario.add(lblHora, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 1;
        panelFormulario.add(txtHora, gbc);

        // Panel de botones.
        JPanel panelBotones = new JPanel(
                new GridLayout(1, 3, 8, 0)
        );

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        panelFormulario.add(panelBotones, gbc);

        // PANEL FILTROS.
        JPanel panelFiltros = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 5)
        );

        JLabel lblFiltroPedido =
                new JLabel("Filtrar por pedido:");

        JLabel lblFiltroRepartidor =
                new JLabel("Filtrar por repartidor:");

        cmbFiltroPedido = new JComboBox<>();
        cmbFiltroRepartidor = new JComboBox<>();

        // Ajustamos el tamaño de los filtros.
        cmbFiltroPedido.setPreferredSize(
                new Dimension(285, 30)
        );

        cmbFiltroRepartidor.setPreferredSize(
                new Dimension(285, 30)
        );

        panelFiltros.add(lblFiltroPedido);
        panelFiltros.add(cmbFiltroPedido);

        panelFiltros.add(lblFiltroRepartidor);
        panelFiltros.add(cmbFiltroRepartidor);

        // PANEL SUPERIOR.
        JPanel panelSuperior = new JPanel(
                new BorderLayout(10, 10)
        );

        panelSuperior.add(
                panelFormulario,
                BorderLayout.NORTH
        );

        panelSuperior.add(
                panelFiltros,
                BorderLayout.SOUTH
        );

        // TABLA: ENTREGAS.
        modeloTabla = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Pedido",
                        "Repartidor",
                        "Fecha",
                        "Hora"
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

        tablaEntregas = new JTable(modeloTabla);

        tablaEntregas.setRowHeight(25);

        // SE AGREGA: componentes al panel principal.
        panelPrincipal.add(
                panelSuperior,
                BorderLayout.NORTH
        );

        panelPrincipal.add(
                new JScrollPane(tablaEntregas),
                BorderLayout.CENTER
        );

        add(panelPrincipal);

        // Eventos de los botones.
        btnRegistrar.addActionListener(
                e -> registrarEntrega()
        );

        btnActualizar.addActionListener(
                e -> actualizarEntrega()
        );

        btnEliminar.addActionListener(
                e -> eliminarEntrega()
        );

        // Eventos de los filtros.
        cmbFiltroPedido.addActionListener(
                e -> aplicarFiltros()
        );

        cmbFiltroRepartidor.addActionListener(
                e -> aplicarFiltros()
        );

        // Evento al seleccionar una entrega.
        tablaEntregas.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        int fila =
                                tablaEntregas.getSelectedRow();

                        if (fila >= 0) {
                            cargarEntregaSeleccionada(fila);
                        }
                    }
                });

        // Se cargan los datos al abrir la ventana.
        cargarCombos();
        cargarEntregas();

        // Se actualiza los combos al volver a la ventana.
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowActivated(WindowEvent e) {

                cargarCombos();
                cargarEntregas();
            }
        });
    }

    // Carga pedidos y repartidores desde la base de datos.
    private void cargarCombos() {

        try {

            cmbPedido.removeAllItems();
            cmbRepartidor.removeAllItems();

            cmbFiltroPedido.removeAllItems();
            cmbFiltroRepartidor.removeAllItems();

            // Opción para mostrar todas las entregas.
            cmbFiltroPedido.addItem(null);
            cmbFiltroRepartidor.addItem(null);

            // Se carga los pedidos desde la BD.
            List<Pedido> pedidos =
                    pedidoController.listar();

            for (Pedido pedido : pedidos) {

                cmbPedido.addItem(pedido);
                cmbFiltroPedido.addItem(pedido);
            }

            // Se carga los repartidores desde la BD.
            List<Repartidor> repartidores =
                    repartidorController.listar();

            for (Repartidor repartidor : repartidores) {

                cmbRepartidor.addItem(repartidor);
                cmbFiltroRepartidor.addItem(repartidor);
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudieron cargar los pedidos y repartidores:\n"
                            + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Registra una nueva entrega.
    private void registrarEntrega() {

        Pedido pedido =
                (Pedido) cmbPedido.getSelectedItem();

        Repartidor repartidor =
                (Repartidor) cmbRepartidor.getSelectedItem();

        String fechaTexto =
                txtFecha.getText().trim();

        String horaTexto =
                txtHora.getText().trim();

        // Validación del pedido.
        if (pedido == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un pedido.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Validación del repartidor.
        if (repartidor == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un repartidor.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Se convierte y valida la fecha.
        LocalDate fecha =
                convertirFecha(fechaTexto);

        if (fecha == null) {
            return;
        }

        // Se convierte y valida la hora.
        LocalTime hora =
                convertirHora(horaTexto);

        if (hora == null) {
            return;
        }

        try {

            // Obtención de los ID reales de los objetos seleccionados.
            int idPedido = pedido.getId();
            int idRepartidor = repartidor.getId();

            entregaController.crear(
                    idPedido,
                    idRepartidor,
                    fecha,
                    hora
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega registrada correctamente."
            );

            limpiarFormulario();
            cargarEntregas();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar la entrega:\n"
                            + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Actualiza la entrega seleccionada.
    private void actualizarEntrega() {

        int fila =
                tablaEntregas.getSelectedRow();

        // Validación de la existencia de una fila seleccionada.
        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar la entrega que desea modificar.",
                    "Entrega no seleccionada",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Pedido pedido =
                (Pedido) cmbPedido.getSelectedItem();

        Repartidor repartidor =
                (Repartidor) cmbRepartidor.getSelectedItem();

        // Validación del pedido.
        if (pedido == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un pedido.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Validamos el repartidor.
        if (repartidor == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un repartidor.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        LocalDate fecha =
                convertirFecha(
                        txtFecha.getText().trim()
                );

        if (fecha == null) {
            return;
        }

        LocalTime hora =
                convertirHora(
                        txtHora.getText().trim()
                );

        if (hora == null) {
            return;
        }

        int id =
                Integer.parseInt(
                        tablaEntregas
                                .getValueAt(fila, 0)
                                .toString()
                );

        try {

            entregaController.actualizar(
                    id,
                    pedido.getId(),
                    repartidor.getId(),
                    fecha,
                    hora
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega actualizada exitosamente."
            );

            limpiarFormulario();
            cargarEntregas();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo actualizar la entrega:\n"
                            + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Elimina la entrega seleccionada.
    private void eliminarEntrega() {

        int fila =
                tablaEntregas.getSelectedRow();

        // Validación que exista una fila seleccionada.
        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione la entrega que desea eliminar.",
                    "Entrega no seleccionada",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int id =
                Integer.parseInt(
                        tablaEntregas
                                .getValueAt(fila, 0)
                                .toString()
                );

        int respuesta =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Está seguro de eliminar esta entrega?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            entregaController.eliminar(id);

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega eliminada correctamente."
            );

            limpiarFormulario();
            cargarEntregas();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar la entrega:\n"
                            + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Carga las entregas desde la base de datos.
    private void cargarEntregas() {

        try {

            List<Entrega> entregas =
                    entregaController.listar();

            modeloTabla.setRowCount(0);

            for (Entrega entrega : entregas) {

                Pedido pedido =
                        buscarPedido(
                                entrega.getIdPedido()
                        );

                Repartidor repartidor =
                        buscarRepartidor(
                                entrega.getIdRepartidor()
                        );

                String textoPedido =
                        pedido != null
                                ? pedido.toString()
                                : "#" + entrega.getIdPedido();

                String textoRepartidor =
                        repartidor != null
                                ? repartidor.toString()
                                : "#" + entrega.getIdRepartidor();

                modeloTabla.addRow(
                        new Object[]{
                                entrega.getId(),
                                textoPedido,
                                textoRepartidor,
                                entrega.getFecha(),
                                entrega.getHora().format(formatoHora)
                        }
                );
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudieron cargar las entregas:\n"
                            + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Busca un pedido por su ID.
    private Pedido buscarPedido(int id) {

        for (int i = 0;
             i < cmbPedido.getItemCount();
             i++) {

            Pedido pedido =
                    cmbPedido.getItemAt(i);

            if (pedido.getId() == id) {
                return pedido;
            }
        }

        return null;
    }

    // Busca un repartidor por su ID.
    private Repartidor buscarRepartidor(int id) {

        for (int i = 0;
             i < cmbRepartidor.getItemCount();
             i++) {

            Repartidor repartidor =
                    cmbRepartidor.getItemAt(i);

            if (repartidor.getId() == id) {
                return repartidor;
            }
        }

        return null;
    }

    // Aplica los filtros de pedido y repartidor.
    private void aplicarFiltros() {

        Pedido pedidoFiltro =
                (Pedido) cmbFiltroPedido.getSelectedItem();

        Repartidor repartidorFiltro =
                (Repartidor) cmbFiltroRepartidor.getSelectedItem();

        try {

            List<Entrega> entregas =
                    entregaController.listar();

            modeloTabla.setRowCount(0);

            for (Entrega entrega : entregas) {

                boolean coincidePedido =
                        pedidoFiltro == null
                                || entrega.getIdPedido()
                                == pedidoFiltro.getId();

                boolean coincideRepartidor =
                        repartidorFiltro == null
                                || entrega.getIdRepartidor()
                                == repartidorFiltro.getId();

                if (coincidePedido
                        && coincideRepartidor) {

                    Pedido pedido =
                            buscarPedido(
                                    entrega.getIdPedido()
                            );

                    Repartidor repartidor =
                            buscarRepartidor(
                                    entrega.getIdRepartidor()
                            );

                    String textoPedido =
                            pedido != null
                                    ? pedido.toString()
                                    : "#" + entrega.getIdPedido();

                    String textoRepartidor =
                            repartidor != null
                                    ? repartidor.toString()
                                    : "#" + entrega.getIdRepartidor();

                    modeloTabla.addRow(
                            new Object[]{
                                    entrega.getId(),
                                    textoPedido,
                                    textoRepartidor,
                                    entrega.getFecha(),
                                    entrega.getHora().format(formatoHora)
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

    // Carga los datos de la entrega seleccionada.
    private void cargarEntregaSeleccionada(int fila) {

        int idEntrega =
                Integer.parseInt(
                        tablaEntregas
                                .getValueAt(fila, 0)
                                .toString()
                );

        try {

            Entrega entrega =
                    entregaController.buscar(idEntrega)
                            .orElse(null);

            if (entrega == null) {
                return;
            }

            seleccionarPedidoPorId(
                    entrega.getIdPedido()
            );

            seleccionarRepartidorPorId(
                    entrega.getIdRepartidor()
            );

            txtFecha.setText(
                    entrega.getFecha().toString()
            );

            txtHora.setText(
                    entrega.getHora().format(formatoHora)
            );

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo cargar la entrega seleccionada:\n"
                            + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Selecciona un pedido en el combo usando su ID.
    private void seleccionarPedidoPorId(int idPedido) {

        for (int i = 0;
             i < cmbPedido.getItemCount();
             i++) {

            Pedido pedido =
                    cmbPedido.getItemAt(i);

            if (pedido.getId() == idPedido) {

                cmbPedido.setSelectedIndex(i);
                return;
            }
        }
    }

    // Selecciona un repartidor en el combo usando su ID.
    private void seleccionarRepartidorPorId(
            int idRepartidor) {

        for (int i = 0;
             i < cmbRepartidor.getItemCount();
             i++) {

            Repartidor repartidor =
                    cmbRepartidor.getItemAt(i);

            if (repartidor.getId() == idRepartidor) {

                cmbRepartidor.setSelectedIndex(i);
                return;
            }
        }
    }

    // Valida y convierte la fecha.
    private LocalDate convertirFecha(String texto) {

        if (texto.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar una fecha.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            txtFecha.requestFocus();
            return null;
        }

        try {

            return LocalDate.parse(
                    texto,
                    formatoFecha
            );

        } catch (DateTimeParseException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "La fecha debe cumplir con el formato:\n"
                            + "yyyy-MM-dd\n\n"
                            + "Ejemplo: 2026-10-04",
                    "Fecha inválida",
                    JOptionPane.WARNING_MESSAGE
            );

            txtFecha.requestFocus();
            return null;
        }
    }

    // Valida y convierte la hora.
    private LocalTime convertirHora(String texto) {

        if (texto.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar una hora.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            txtHora.requestFocus();
            return null;
        }

        try {

            return LocalTime.parse(
                    texto,
                    formatoHora
            );

        } catch (DateTimeParseException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "La hora debe cumplir con el formato:\n"
                            + "HH:mm\n\n"
                            + "Ejemplo: 14:30",
                    "Hora inválida",
                    JOptionPane.WARNING_MESSAGE
            );

            txtHora.requestFocus();
            return null;
        }
    }

    // Limpia el formulario.
    private void limpiarFormulario() {

        if (cmbPedido.getItemCount() > 0) {
            cmbPedido.setSelectedIndex(0);
        }

        if (cmbRepartidor.getItemCount() > 0) {
            cmbRepartidor.setSelectedIndex(0);
        }

        txtFecha.setText("");
        txtHora.setText("");

        tablaEntregas.clearSelection();

        txtFecha.requestFocus();
    }
}