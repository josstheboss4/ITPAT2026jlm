package dojomanager.gui;

import dojomanager.data.DataManager;
import dojomanager.model.DojoInfo;
import dojomanager.util.Validation;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Dojo location & contact info.
 * Opens Google Maps in the browser (simple map integration for PAT).
 */
public class LocationPanel extends JPanel {

    private final DataManager dataManager;
    private final JLabel nameDisplay;
    private final JLabel addressDisplay;
    private final JLabel cityDisplay;
    private final JLabel phoneDisplay;
    private final JLabel emailDisplay;
    private final JLabel senseiDisplay;
    private final JTextArea notesDisplay;

    public LocationPanel(DataManager dataManager) {
        this.dataManager = dataManager;
        setLayout(new BorderLayout(0, 0));
        setBackground(UITheme.BG);
        setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(UITheme.sectionTitle("Dojo Location & Contact"), BorderLayout.WEST);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);
        JButton editBtn = UITheme.secondaryButton("Edit details");
        JButton mapBtn = UITheme.primaryButton("Open in Google Maps");
        editBtn.addActionListener(e -> openEditDialog());
        mapBtn.addActionListener(e -> openGoogleMaps());
        actions.add(editBtn);
        actions.add(mapBtn);
        top.add(actions, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Location card
        JPanel card = new JPanel(new BorderLayout(20, 0));
        card.setBackground(UITheme.PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER, 1),
                BorderFactory.createEmptyBorder(28, 28, 28, 28)
        ));
        card.setAlignmentX(LEFT_ALIGNMENT);

        // Left accent strip look via red title bar
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        nameDisplay = new JLabel("Dojo");
        nameDisplay.setFont(new Font("Segoe UI", Font.BOLD, 22));
        nameDisplay.setForeground(UITheme.CHARCOAL);
        JLabel pin = new JLabel("LOCATION");
        pin.setFont(new Font("Segoe UI", Font.BOLD, 11));
        pin.setOpaque(true);
        pin.setBackground(UITheme.PINK_DEEP);
        pin.setForeground(Color.WHITE);
        pin.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        pin.setHorizontalAlignment(SwingConstants.CENTER);
        header.add(nameDisplay, BorderLayout.WEST);
        header.add(pin, BorderLayout.EAST);

        JPanel details = new JPanel(new GridLayout(0, 1, 0, 10));
        details.setOpaque(false);
        details.setBorder(BorderFactory.createEmptyBorder(18, 0, 10, 0));

        senseiDisplay = detailLabel();
        addressDisplay = detailLabel();
        cityDisplay = detailLabel();
        phoneDisplay = detailLabel();
        emailDisplay = detailLabel();

        details.add(senseiDisplay);
        details.add(addressDisplay);
        details.add(cityDisplay);
        details.add(phoneDisplay);
        details.add(emailDisplay);

        notesDisplay = new JTextArea(3, 40);
        notesDisplay.setFont(UITheme.BODY);
        notesDisplay.setLineWrap(true);
        notesDisplay.setWrapStyleWord(true);
        notesDisplay.setEditable(false);
        notesDisplay.setBackground(UITheme.TABLE_ALT);
        notesDisplay.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER, 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));

        JLabel notesTitle = new JLabel("About the dojo");
        notesTitle.setFont(UITheme.SMALL);
        notesTitle.setForeground(UITheme.MUTED);

        JPanel notesWrap = new JPanel(new BorderLayout(0, 6));
        notesWrap.setOpaque(false);
        notesWrap.add(notesTitle, BorderLayout.NORTH);
        notesWrap.add(notesDisplay, BorderLayout.CENTER);

        JPanel body = new JPanel(new BorderLayout(0, 16));
        body.setOpaque(false);
        body.add(header, BorderLayout.NORTH);
        body.add(details, BorderLayout.CENTER);
        body.add(notesWrap, BorderLayout.SOUTH);

        card.add(body, BorderLayout.CENTER);

        JLabel tip = new JLabel(
                "<html><body style='color:#6E6964;'>"
                + "Click <b>Open in Google Maps</b> to view the dojo location in your browser."
                + "</body></html>"
        );
        tip.setFont(UITheme.SMALL);
        tip.setBorder(BorderFactory.createEmptyBorder(14, 2, 0, 0));
        tip.setAlignmentX(LEFT_ALIGNMENT);

        content.add(card);
        content.add(tip);

        add(new JScrollPane(content) {{
            setBorder(null);
            getViewport().setBackground(UITheme.BG);
            setOpaque(false);
        }}, BorderLayout.CENTER);

        refreshDisplay();
    }

    private JLabel detailLabel() {
        JLabel label = new JLabel(" ");
        label.setFont(UITheme.BODY);
        label.setForeground(UITheme.CHARCOAL);
        return label;
    }

    public void refreshDisplay() {
        DojoInfo info = dataManager.getDojoInfo();
        nameDisplay.setText(info.getDojoName());
        senseiDisplay.setText("Sensei:  " + info.getSenseiName());
        addressDisplay.setText("Address:  " + info.getAddress());
        cityDisplay.setText("City:  " + info.getCity());
        phoneDisplay.setText("Phone:  " + info.getPhone());
        emailDisplay.setText("Email:  " + info.getEmail());
        notesDisplay.setText(info.getNotes());
    }

    private void openGoogleMaps() {
        try {
            DojoInfo info = dataManager.getDojoInfo();
            String query = URLEncoder.encode(info.getFullAddress(), StandardCharsets.UTF_8.toString());
            // Google Maps search URL — opens in the default browser
            URI mapsUri = new URI("https://www.google.com/maps/search/?api=1&query=" + query);

            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(mapsUri);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Could not open browser.\nMaps link:\n" + mapsUri,
                        "Google Maps",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Could not open Google Maps: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openEditDialog() {
        DojoInfo info = dataManager.getDojoInfo();

        JTextField nameField = new JTextField(info.getDojoName());
        JTextField senseiField = new JTextField(info.getSenseiName());
        JTextField addressField = new JTextField(info.getAddress());
        JTextField cityField = new JTextField(info.getCity());
        JTextField phoneField = new JTextField(info.getPhone());
        JTextField emailField = new JTextField(info.getEmail());
        JTextArea notesField = new JTextArea(info.getNotes(), 3, 25);
        notesField.setLineWrap(true);
        notesField.setWrapStyleWord(true);

        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.add(new JLabel("Dojo name:"));
        form.add(nameField);
        form.add(new JLabel("Sensei:"));
        form.add(senseiField);
        form.add(new JLabel("Street address:"));
        form.add(addressField);
        form.add(new JLabel("City / Country:"));
        form.add(cityField);
        form.add(new JLabel("Phone:"));
        form.add(phoneField);
        form.add(new JLabel("Email:"));
        form.add(emailField);

        JPanel wrap = new JPanel(new BorderLayout(0, 8));
        wrap.add(form, BorderLayout.CENTER);
        wrap.add(new JScrollPane(notesField), BorderLayout.SOUTH);

        int result = JOptionPane.showConfirmDialog(
                this, wrap, "Edit Dojo Details",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            if (!Validation.isPresent(nameField.getText())
                    || !Validation.isPresent(addressField.getText())) {
                JOptionPane.showMessageDialog(this, "Dojo name and address are required.");
                return;
            }
            if (!Validation.isValidPhone(phoneField.getText())) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid dojo phone number (10 to 13 digits).");
                return;
            }
            if (!Validation.isValidEmail(emailField.getText())) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid dojo email address, e.g. info@dojo.co.za");
                return;
            }
            info.setDojoName(nameField.getText().trim());
            info.setSenseiName(senseiField.getText().trim());
            info.setAddress(addressField.getText().trim());
            info.setCity(cityField.getText().trim());
            info.setPhone(phoneField.getText().trim());
            info.setEmail(emailField.getText().trim());
            info.setNotes(notesField.getText().trim());
            dataManager.updateDojoInfo(info);
            refreshDisplay();
            JOptionPane.showMessageDialog(this, "Dojo details saved.");
        }
    }
}
