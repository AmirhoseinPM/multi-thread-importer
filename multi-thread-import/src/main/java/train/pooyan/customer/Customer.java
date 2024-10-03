package train.pooyan.customer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Customer {

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;

    private String surname;

    private String address;

    private String zipCode;

    private String nationalId;

    private LocalDate birthDate;

    public Customer() {}
    public Customer(String name, String surname, String address, String zipCode, String nationalId, LocalDate birthDate) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.zipCode = zipCode;
        this.nationalId = nationalId;
        this.birthDate = birthDate;
    }
    
    public Customer(long id, String name, String surname, String address, String zipCode, String nationalId, LocalDate birthDate) {
        this.id = id;
    	this.name = name;
        this.surname = surname;
        this.address = address;
        this.zipCode = zipCode;
        this.nationalId = nationalId;
        this.birthDate = birthDate;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", nationalId='" + nationalId + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
