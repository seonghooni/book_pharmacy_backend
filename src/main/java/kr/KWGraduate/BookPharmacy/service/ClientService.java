package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.dto.ClientDto;
import kr.KWGraduate.BookPharmacy.entity.Client;
import kr.KWGraduate.BookPharmacy.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Transactional
    public String signUp(ClientDto clientDto){
        Client client = clientDto.toEntity();

        validateDuplicateClient(client);
        //controller에서 예외처리
        return clientRepository.save(client);
    }

    private void validateDuplicateClient(Client client) {

        if(isExistId(client.getId())){
            throw new IllegalArgumentException("이미 id가 존재합니다.");
        }
        if(isExistNickname(client.getNickname())){
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
        if(isExistEmail(client.getEmail())){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
    }

    public Client findById(String id){
        return clientRepository.findById(id);
    }
    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    @Transactional
    public void removeClient(Client client){
        clientRepository.delete(client);
    }
    //remove하는데 id로만 받을지 dto를 받을지

    @Transactional
    public void updateClient(ClientDto clientDto){
        Client findClient = clientRepository.findById(clientDto.getId());

        findClient.update(clientDto.getPassword(),clientDto.getNickname(),clientDto.getOccupation());
    }
    public Long getClientsCount(){
        return clientRepository.count();
    }

    public boolean isExistId(String id){
        Client findClient = clientRepository.findById(id);
        if(findClient == null){
            return false;
        }
        else {
            return true;
        }
    }
    public boolean isExistNickname(String nickname){
        List<Client> findClients = clientRepository.findByNickname(nickname);
        if(!findClients.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isExistEmail(String email){
        List<Client> findClients = clientRepository.findByEmail(email);
        if (!findClients.isEmpty()) {
            return true;
        }
        else{
            return false;
        }
    }

    public boolean canLogin(String id, String password){
        //예외를 돌리는 것이 나은지
        return clientRepository.findById(id).isEqualPassword(password);
    }

    public boolean setVerificationCode(String name, String email, String code){
    // client에 코드 있어야함
        //예외를 호출하는 것이 더 나을 듯?
        List<Client> clients = clientRepository.findByEmail(email);
        if(clients.isEmpty()){
            return false;
        }

        Client client = clients.get(0);
        if(client.isEqualName(name)){
            return false;
        }
        //client에 코드 삽입
        return true;
    }
    public String findByCode(String id, String code){
        Client client = clientRepository.findById(id);
        //client code비교
        return client.getId();
    }




}
