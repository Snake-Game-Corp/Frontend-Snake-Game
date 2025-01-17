package com.lcaohoanq.views.usersregister;

import com.lcaohoanq.utils.ApiUtils;
import com.lcaohoanq.utils.ValidateUtils;
import com.lcaohoanq.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@PageTitle("Snake Game")
@Getter
@Setter
@Route(value = "users/register", layout = MainLayout.class)
public class UsersRegisterView extends Composite<VerticalLayout> {

    private H3 title = new H3();
    private TextField textField_Email_Phone = new TextField("Email or Phone Number");
    private TextField textField_First_Name = new TextField("First Name");
    private TextField textField_Last_Name = new TextField("Last Name");
    private PasswordField textField_Password = new PasswordField("Password");
    private TextField textField_Address = new TextField("Address");
    private DatePicker datePicker_Birthday = new DatePicker("Birthday");

    //chose gender: male, female, others
    private ComboBox<String> select_G = new ComboBox<>("Gender");


    private PasswordField textField_Confirmed_Password = new PasswordField("Confirmed password");
    private FormLayout formLayout2Col = new FormLayout();
    private HorizontalLayout layoutRow = new HorizontalLayout();
    private VerticalLayout layoutColumn2 = new VerticalLayout();
    private Button button_Save = new Button("Save");
    private Button button_Cancel = new Button("Cancel");

    public UsersRegisterView() {
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        initComponent();
        doAction();
    }

    private void initComponent() {
        title.setText("Register");
        title.setWidth("min-content");

        textField_Email_Phone.setWidth("100%");
        textField_First_Name.setWidth("min-content");
        textField_Last_Name.setWidth("min-content");
        textField_Password.setWidth("min-content");
        textField_Confirmed_Password.setWidth("min-content");

        textField_Address.setWidth("100%");
        datePicker_Birthday.setWidth("100%");
        select_G.setWidth("100%");
        select_G.setItems("MALE", "FEMALE", "OTHER");
        select_G.setValue("MALE"); //default value

        layoutRow.setWidthFull();
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("min-content");

        layoutColumn2.setWidthFull();
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        layoutColumn2.setFlexGrow(1.0, layoutRow);

        button_Save.setWidth("min-content");
        button_Save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button_Save.getStyle().set("cursor", "pointer");

        button_Cancel.setWidth("min-content");

        getContent().setFlexGrow(1.0, layoutColumn2);

        formLayout2Col.setWidth("100%");

        getContent().add(layoutColumn2);
        layoutColumn2.add(title);
        layoutColumn2.add(textField_Email_Phone);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(textField_First_Name);
        formLayout2Col.add(textField_Last_Name);
        formLayout2Col.add(textField_Address);
        formLayout2Col.add(datePicker_Birthday);
        formLayout2Col.add(textField_Password);
        formLayout2Col.add(textField_Confirmed_Password);
        formLayout2Col.add(select_G);
        layoutColumn2.add(layoutRow);
        layoutRow.add(button_Save);
        layoutRow.add(button_Cancel);
    }

    private void validateFields(String fieldName) {
        String emailPhone = textField_Email_Phone.getValue();
        String firstName = textField_First_Name.getValue();
        String lastName = textField_Last_Name.getValue();
        String password = textField_Password.getValue();
        String address = textField_Address.getValue();
        LocalDateTime birthday =
            datePicker_Birthday.getValue() != null ? datePicker_Birthday.getValue().atStartOfDay()
                : null;
        String gender = select_G.getValue();
        String confirmedPassword = textField_Confirmed_Password.getValue();

        switch (fieldName) {
            case "emailPhone":
                if(textField_Email_Phone.isEmpty()){
                    textField_Email_Phone.setErrorMessage("Email or Phone Number is required");
                    textField_Email_Phone.setInvalid(true);
                }else {
                    if(ValidateUtils.checkTypeAccount(emailPhone)){
                        if (!emailPhone.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                            textField_Email_Phone.setErrorMessage("Invalid email format");
                            textField_Email_Phone.setInvalid(true);
                        } else {
                            textField_Email_Phone.setInvalid(false);
                        }
                    }else{
                        if (!emailPhone.matches("(84|0[3|5|7|8|9])[0-9]{8}")) {
                            textField_Email_Phone.setErrorMessage("Invalid phone number format");
                            textField_Email_Phone.setInvalid(true);
                        }else{
                            textField_Email_Phone.setInvalid(false);
                        }
                    }
                }
                break;
            case "firstName":
                textField_First_Name.setInvalid(false);
                if (firstName.isEmpty()) {
                    textField_First_Name.setErrorMessage("First name is required");
                    textField_First_Name.setInvalid(true);
                }
                break;
            case "lastName":
                textField_Last_Name.setInvalid(false);
                if(lastName.isEmpty()){
                    textField_Last_Name.setErrorMessage("Last name is required");
                    textField_Last_Name.setInvalid(true);
                }
                break;
            case "password":
                textField_Password.setInvalid(false);
                if(password.isEmpty()){
                    textField_Password.setErrorMessage("Password is required");
                    textField_Password.setInvalid(true);
                }
                break;
            case "confirmedPassword":
                textField_Confirmed_Password.setInvalid(false);
                if(confirmedPassword.isEmpty()){
                    textField_Confirmed_Password.setErrorMessage("Confirmed password is required");
                    textField_Confirmed_Password.setInvalid(true);
                } else if(!confirmedPassword.equals(password)){
                    textField_Confirmed_Password.setErrorMessage("Confirmed password does not match");
                    textField_Confirmed_Password.setInvalid(true);
                }
                break;
            case "address":
                textField_Address.setInvalid(false);
                if(address.isEmpty()){
                    textField_Address.setErrorMessage("Address is required");
                    textField_Address.setInvalid(true);
                }
                break;
            case "birthday":
                if(birthday == null){
                    datePicker_Birthday.setInvalid(true);
                    datePicker_Birthday.setErrorMessage("Birthday is required");
                } else {
                    datePicker_Birthday.setInvalid(false);
                }
                break;
            case "gender":
                select_G.setInvalid(false);
                break;
        }
    }

    private void doAction() {
        textField_Email_Phone.addValueChangeListener(event -> validateFields("emailPhone"));
        textField_First_Name.addValueChangeListener(event -> validateFields("firstName"));
        textField_Last_Name.addValueChangeListener(event -> validateFields("lastName"));
        textField_Password.addValueChangeListener(event -> validateFields("password"));
        textField_Confirmed_Password.addValueChangeListener(
            event -> validateFields("confirmedPassword"));
        textField_Address.addValueChangeListener(event -> validateFields("address"));
        datePicker_Birthday.addValueChangeListener(event -> validateFields("birthday"));
        select_G.addValueChangeListener(event -> validateFields("gender"));

        button_Save.addClickListener(event -> {
            validateAllFields();
            if (isFormValid()) {
                try {
                    HttpResponse<String> response = ApiUtils.postRequest(
                        "http://localhost:8081/users/register", fetchData());
                    Dialog dialog;
                    switch (response.statusCode()) {
                        case 200:
                            dialog = new Dialog();
                            dialog.add(new H3("Register successfully"));
                            dialog.open();
                            break;
                        case 400:
                            dialog = new Dialog();
                            dialog.add(new H3("Either email or phone must be provided"));
                            dialog.open();
                            break;
                        default:
                            dialog = new Dialog();
                            dialog.add(new H3("An error occurred while creating a new user"));
                            dialog.open();
                            break;
                    }
                } catch (Exception e) {
                    System.out.println(
                        "An error occurred while creating a new user: " + e.getMessage());
                }
            }
        });
    }

    private Map<String, Object> fetchData(){
        String email_phone = textField_Email_Phone.getValue();
        String first_name = textField_First_Name.getValue();
        String last_name = textField_Last_Name.getValue();
        String password = textField_Password.getValue();
        String address = textField_Address.getValue();
        LocalDateTime birthday = datePicker_Birthday.getValue().atStartOfDay();
        String gender = select_G.getValue();
        String confirmed_password = textField_Confirmed_Password.getValue();

        UserRegisterRequest user = new UserRegisterRequest();
        user.setId(-1L);
        user.setFirstName(first_name);
        user.setLastName(last_name);
        if (ValidateUtils.checkTypeAccount(email_phone)) {
            user.setEmail(email_phone);
            user.setPhone(null);
        } else {
            user.setEmail(null);
            user.setPhone(email_phone);
        }
        user.setPassword(password);
        user.setAddress(address);
        user.setBirthday(birthday.toString());
        user.setGender(gender);
        user.setRole("USER");
        user.setStatus("UNVERIFIED");
        user.setCreated_at(LocalDate.now().toString());
        user.setUpdated_at(LocalDate.now().toString());
        user.setAvatar_url(null);

        Map<String, Object> payload = new HashMap<>();
        payload.put("id", user.getId());
        payload.put("email", user.getEmail());
        payload.put("phone", user.getPhone());
        payload.put("firstName", user.getFirstName());
        payload.put("lastName", user.getLastName());
        payload.put("password", user.getPassword());
        payload.put("address", user.getAddress());
        payload.put("birthday", user.getBirthday());
        payload.put("gender", user.getGender());
        payload.put("role", user.getRole());
        payload.put("status", user.getStatus());
        payload.put("created_at", user.getCreated_at());
        payload.put("updated_at", user.getUpdated_at());
        payload.put("avatar_url", user.getAvatar_url());

        return payload;
    }

    private void validateAllFields() {
        validateFields("emailPhone");
        validateFields("firstName");
        validateFields("lastName");
        validateFields("password");
        validateFields("confirmedPassword");
        validateFields("address");
        validateFields("birthday");
        validateFields("gender");
    }

    private boolean isFormValid() {
        return !textField_Email_Phone.isInvalid() &&
            !textField_First_Name.isInvalid() &&
            !textField_Last_Name.isInvalid() &&
            !textField_Password.isInvalid() &&
            !textField_Confirmed_Password.isInvalid() &&
            !textField_Address.isInvalid() &&
            (datePicker_Birthday.getValue() != null) &&
            (select_G.getValue() != null);
    }

    record SampleItem(String value, String label, Boolean disabled) {

    }

    private void setSelectSampleData(Select select) {
        List<SampleItem> sampleItems = new ArrayList<>();
        sampleItems.add(new SampleItem("state1", "State 1", null));
        sampleItems.add(new SampleItem("state2", "State 2", null));
        sampleItems.add(new SampleItem("state3", "State 3", null));
        select.setItems(sampleItems);
        select.setItemLabelGenerator(item -> ((SampleItem) item).label());
        select.setItemEnabledProvider(item -> !Boolean.TRUE.equals(((SampleItem) item).disabled()));
    }
}
