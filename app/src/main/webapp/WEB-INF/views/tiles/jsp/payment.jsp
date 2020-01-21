
 <!-- ------- PAYMENT FORM ------- -->
<div class="modal fade" id="PaymentModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
            </div>
            <div class="modal-body">
                <form>
                    <!-- Form Title -->
                    <div class="form-heading text-center">
                        <div class="title">Payment Form</div>
                        <p class="title-description">Please provide the billing &amp; payment info below</p>
                    </div>
                    <div class="row">
                        <div class="col-md-12 col-md-offset-2"> 
                        <i class="fa fa-paypal fa-2x" aria-hidden="true"></i> <i class="fa fa-cc-mastercard fa-2x" aria-hidden="true"></i> <i class="fa fa-cc-visa fa-2x" aria-hidden="true"></i> <i class="fa fa-cc-amex fa-2x" aria-hidden="true"></i> <i class="fa fa-cc-stripe fa-2x" aria-hidden="true"></i>
                        <br /> <br /></div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <input type="text" required placeholder="Card Holder's Name" /> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-8">
                            <input type="text" placeholder="Card Number" /> 
                        </div>
                        <div class="col-md-4">
                            <select class="form-control">
                                <option disabled="" selected=""> Card Type</option>
                                <option value="MasterCard"> MasterCard</option>
                                <option value="Visa"> Visa</option>
                                <option value="America Express"> America Express</option>
                                <option value="Discover"> Discover</option>
                                <option value="Other"> Other</option>
                            </select>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <select class="form-control">
                                <option disabled="" selected="">Month</option>
                                <option value="1"> 1 </option>
                                <option value="2"> 2 </option>
                                <option value="3"> 3 </option>
                                <option value="4"> 4 </option>
                                <option value="5"> 5 </option>
                                <option value="6"> 6 </option>
                                <option value="7"> 7 </option>
                                <option value="8"> 8 </option>
                                <option value="9"> 9 </option>
                                <option value="10"> 10 </option>
                                <option value="11"> 11 </option>
                                <option value="12"> 12 </option>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <select class="form-control">
                                <option disabled="" selected="">Year</option>
                                <option value="2017"> 2017 </option>
                                <option value="2018"> 2018 </option>
                                <option value="2019"> 2019 </option>
                                <option value="2020"> 2020 </option>
                                <option value="2021"> 2021 </option>
                                <option value="2022"> 2022 </option>
                                <option value="2023"> 2023 </option>
                                <option value="2024"> 2024 </option>
                                <option value="2025"> 2025 </option>
                                <option value="2026"> 2026 </option>
                                <option value="2027"> 2027 </option>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <input type="text" placeholder="CCV" /> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <input type="checkbox" />
                            <label>Do you agree to the <a href="#">terms and conditions?</a></label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 text-center">
                            <button class="btn btn-md btn-danger">Place Order</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- ------- PAYMENT FORM Ends ------- -->