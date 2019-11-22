package com.docutools.matheus.footballmanager.controller;

import com.docutools.matheus.footballmanager.dto.MemberAddDTO;
import com.docutools.matheus.footballmanager.dto.MemberDTO;
import com.docutools.matheus.footballmanager.dto.MemberUpdateDTO;
import com.docutools.matheus.footballmanager.dto.ValidList;
import com.docutools.matheus.footballmanager.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Api(value = "Authenticated JWT Member")
@RestController
@RequestMapping("/api/auth/members")
public class AuthenticatedMemberController extends MemberController{
}
