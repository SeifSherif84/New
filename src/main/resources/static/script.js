// حل بديل لضمان عمل زر الإضافة
function setupAddEventButton() {
    let addBtn = document.getElementById('add-event-btn');

    if (!addBtn) {
        // إنشاء الزر إذا لم يكن موجوداً
        addBtn = document.createElement('button');
        addBtn.id = 'add-event-btn';
        addBtn.className = 'add-event-btn';
        addBtn.innerHTML = '<i class="fas fa-plus"></i>';
        document.body.appendChild(addBtn);
        console.log('تم إنشاء زر إضافة جديد');
    }

    return addBtn;
}

// تهيئة زر الإضافة عند تحميل الصفحة
document.addEventListener('DOMContentLoaded', function() {
    const addBtn = setupAddEventButton();
    const eventModal = document.getElementById('event-modal');

    addBtn.addEventListener('click', function() {
        console.log('تم النقر على زر الإضافة');
        if (eventModal) {
            eventModal.style.display = 'flex';
            eventModal.classList.add('active');
        } else {
            console.error('نموذج الحدث غير موجود');
        }
    });
});

document.addEventListener('DOMContentLoaded', function() {
    // DOM Elements
    const dayViewBtn = document.getElementById('day-view');
    const monthViewBtn = document.getElementById('month-view');
    const listViewBtn = document.getElementById('list-view');
    const dayViewContainer = document.getElementById('day-view-container');
    const monthViewContainer = document.getElementById('month-view-container');
    const listViewContainer = document.getElementById('list-view-container');
    const prevDayBtn = document.getElementById('prev-day');
    const nextDayBtn = document.getElementById('next-day');
    const prevMonthBtn = document.getElementById('prev-month');
    const nextMonthBtn = document.getElementById('next-month');
    const currentDateEl = document.getElementById('current-date');
    const currentMonthEl = document.getElementById('current-month');
    const dayTimeSlots = document.getElementById('day-time-slots');
    const monthGrid = document.getElementById('month-grid');
    const upcomingEventsList = document.getElementById('upcoming-events-list');
    const allEventsList = document.getElementById('all-events-list');
    const addEventBtn = document.getElementById('add-event-btn');
    const eventModal = document.getElementById('event-modal');
    const eventForm = document.getElementById('event-form');
    const cancelEventBtn = document.getElementById('cancel-event');
    const closeModalBtns = document.querySelectorAll('.close-btn');
    const invitationModal = document.getElementById('invitation-modal');
    const acceptInvitationBtn = document.getElementById('accept-invitation');
    const declineInvitationBtn = document.getElementById('decline-invitation');

    // State
    let currentView = 'day';
    let currentDate = new Date();
    let currentMonth = new Date().getMonth();
    let currentYear = new Date().getFullYear();
    let events = [];
    let invitations = [];

    // Sample data - in a real app, this would come from your backend
    function initializeSampleData() {
        const today = new Date();
        const tomorrow = new Date(today);
        tomorrow.setDate(tomorrow.getDate() + 1);

        events = [
            {
                id: 1,
                title: 'Team Meeting',
                date: formatDate(today),
                time: '09:00',
                duration: 60,
                description: 'Weekly team sync',
                reminder: 15,
                invitees: ['colleague1@example.com', 'colleague2@example.com'],
                accepted: ['colleague1@example.com'],
                declined: []
            },
            {
                id: 2,
                title: 'Lunch with Sarah',
                date: formatDate(today),
                time: '12:30',
                duration: 45,
                description: 'Discuss project collaboration',
                reminder: 0,
                invitees: ['sarah@example.com'],
                accepted: ['sarah@example.com'],
                declined: []
            },
            {
                id: 3,
                title: 'Doctor Appointment',
                date: formatDate(tomorrow),
                time: '15:00',
                duration: 30,
                description: 'Annual checkup',
                reminder: 60,
                invitees: [],
                accepted: [],
                declined: []
            }
        ];

        invitations = [
            {
                id: 101,
                eventId: 4,
                title: 'Project Review',
                date: formatDate(tomorrow),
                time: '14:00',
                duration: 90,
                description: 'Quarterly project review with stakeholders',
                sender: 'manager@example.com'
            }
        ];
    }

    // Helper functions
    function formatDate(date) {
        const d = new Date(date);
        let month = '' + (d.getMonth() + 1);
        let day = '' + d.getDate();
        const year = d.getFullYear();

        if (month.length < 2) month = '0' + month;
        if (day.length < 2) day = '0' + day;

        return [year, month, day].join('-');
    }

    function formatTime(time) {
        const [hours, minutes] = time.split(':');
        const hour = parseInt(hours);
        return hour > 12 ? `${hour - 12}:${minutes} PM` : `${hour}:${minutes} AM`;
    }

    function getDayName(date) {
        const days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
        return days[date.getDay()];
    }

    function getMonthName(month) {
        const months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
        return months[month];
    }

    function isToday(date) {
        const today = new Date();
        return date.getDate() === today.getDate() &&
            date.getMonth() === today.getMonth() &&
            date.getFullYear() === today.getFullYear();
    }

    function compareEvents(a, b) {
        const dateA = new Date(`${a.date}T${a.time}`);
        const dateB = new Date(`${b.date}T${b.time}`);
        return dateA - dateB;
    }

    // View rendering functions
    function renderDayView() {
        const dayName = getDayName(currentDate);
        const formattedDate = currentDate.toLocaleDateString('en-US', {
            weekday: 'long',
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        });

        currentDateEl.textContent = formattedDate;

        // Clear previous time slots
        dayTimeSlots.innerHTML = '';

        // Create time slots for the day (8am to 8pm)
        for (let hour = 8; hour <= 20; hour++) {
            const timeSlot = document.createElement('div');
            timeSlot.className = 'time-slot';

            const timeLabel = document.createElement('div');
            timeLabel.className = 'time-label';
            timeLabel.textContent = hour > 12 ? `${hour - 12} PM` : `${hour} AM`;

            const timeEvents = document.createElement('div');
            timeEvents.className = 'time-events';

            // Find events for this hour
            const hourEvents = events.filter(event => {
                const eventHour = parseInt(event.time.split(':')[0]);
                return event.date === formatDate(currentDate) && eventHour === hour;
            });

            // Sort events by time
            hourEvents.sort((a, b) => a.time.localeCompare(b.time));

            // Add events to the time slot
            hourEvents.forEach(event => {
                const eventEl = document.createElement('div');
                eventEl.className = 'calendar-event';
                eventEl.innerHTML = `
                    <strong>${event.title}</strong>
                    <div>${formatTime(event.time)} - ${event.description}</div>
                `;
                eventEl.addEventListener('click', () => openEventModal(event));
                timeEvents.appendChild(eventEl);
            });

            // Add empty state if no events
            if (hourEvents.length === 0) {
                const emptyEvent = document.createElement('div');
                emptyEvent.className = 'calendar-event empty';
                emptyEvent.textContent = 'No events';
                timeEvents.appendChild(emptyEvent);
            }

            timeSlot.appendChild(timeLabel);
            timeSlot.appendChild(timeEvents);
            dayTimeSlots.appendChild(timeSlot);
        }
    }

    function renderMonthView() {
        currentMonthEl.textContent = `${getMonthName(currentMonth)} ${currentYear}`;

        // Clear previous month grid
        monthGrid.innerHTML = '';

        // Add day headers
        const days = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
        days.forEach(day => {
            const dayHeader = document.createElement('div');
            dayHeader.className = 'month-day-header';
            dayHeader.textContent = day;
            monthGrid.appendChild(dayHeader);
        });

        // Get first day of month and how many days in month
        const firstDay = new Date(currentYear, currentMonth, 1).getDay();
        const daysInMonth = new Date(currentYear, currentMonth + 1, 0).getDate();

        // Add empty slots for days before the first day of the month
        for (let i = 0; i < firstDay; i++) {
            const emptyDay = document.createElement('div');
            emptyDay.className = 'month-day empty';
            monthGrid.appendChild(emptyDay);
        }

        // Add days of the month
        for (let day = 1; day <= daysInMonth; day++) {
            const dayEl = document.createElement('div');
            dayEl.className = 'month-day';

            const date = new Date(currentYear, currentMonth, day);
            if (isToday(date)) {
                dayEl.classList.add('today');
            }

            const dayNumber = document.createElement('div');
            dayNumber.className = 'month-day-number';
            dayNumber.textContent = day;
            dayEl.appendChild(dayNumber);

            const dayEvents = document.createElement('div');
            dayEvents.className = 'month-day-events';

            // Find events for this day
            const formattedDate = formatDate(date);
            const dayEventsData = events.filter(event => event.date === formattedDate);

            // Add events to the day (limit to 2 for display)
            dayEventsData.slice(0, 2).forEach(event => {
                const eventEl = document.createElement('div');
                eventEl.className = 'month-day-event';
                eventEl.textContent = `${formatTime(event.time)} ${event.title}`;
                eventEl.addEventListener('click', () => openEventModal(event));
                dayEvents.appendChild(eventEl);
            });

            // Add more indicator if there are more events
            if (dayEventsData.length > 2) {
                const moreEl = document.createElement('div');
                moreEl.className = 'month-day-event';
                moreEl.textContent = `+${dayEventsData.length - 2} more`;
                dayEvents.appendChild(moreEl);
            }

            dayEl.appendChild(dayEvents);
            dayEl.addEventListener('click', () => {
                currentDate = date;
                switchToView('day');
            });

            monthGrid.appendChild(dayEl);
        }
    }

    function renderListView() {
        // Clear previous list
        allEventsList.innerHTML = '';

        // Sort events by date and time
        const sortedEvents = [...events].sort(compareEvents);

        // Group events by date
        const eventsByDate = {};
        sortedEvents.forEach(event => {
            if (!eventsByDate[event.date]) {
                eventsByDate[event.date] = [];
            }
            eventsByDate[event.date].push(event);
        });

        // Add events to the list
        for (const date in eventsByDate) {
            const dateHeader = document.createElement('h3');
            const dateObj = new Date(date);
            dateHeader.textContent = dateObj.toLocaleDateString('en-US', {
                weekday: 'long',
                year: 'numeric',
                month: 'long',
                day: 'numeric'
            });
            allEventsList.appendChild(dateHeader);

            eventsByDate[date].forEach(event => {
                const eventEl = document.createElement('div');
                eventEl.className = 'event-item';
                eventEl.innerHTML = `
                    <h4>${event.title}</h4>
                    <div class="event-time">${formatTime(event.time)} - ${event.duration} min</div>
                    <div>${event.description}</div>
                `;
                eventEl.addEventListener('click', () => openEventModal(event));
                allEventsList.appendChild(eventEl);
            });
        }

        // Add empty state if no events
        if (sortedEvents.length === 0) {
            const emptyState = document.createElement('div');
            emptyState.className = 'event-item empty';
            emptyState.textContent = 'No events scheduled';
            allEventsList.appendChild(emptyState);
        }
    }

    function renderUpcomingEvents() {
        // Clear previous list
        upcomingEventsList.innerHTML = '';

        // Get today's date and filter events from today onward
        const today = formatDate(new Date());
        const upcoming = events
            .filter(event => event.date >= today)
            .sort(compareEvents)
            .slice(0, 5); // Limit to 5 upcoming events

        // Add events to the list
        upcoming.forEach(event => {
            const eventEl = document.createElement('div');
            eventEl.className = 'event-item';
            eventEl.innerHTML = `
                <h4>${event.title}</h4>
                <div class="event-time">${new Date(`${event.date}T${event.time}`).toLocaleString()}</div>
            `;
            eventEl.addEventListener('click', () => openEventModal(event));
            upcomingEventsList.appendChild(eventEl);
        });

        // Add empty state if no upcoming events
        if (upcoming.length === 0) {
            const emptyState = document.createElement('div');
            emptyState.className = 'event-item empty';
            emptyState.textContent = 'No upcoming events';
            upcomingEventsList.appendChild(emptyState);
        }
    }

    function checkForInvitations() {
        if (invitations.length > 0) {
            openInvitationModal(invitations[0]);
        }
    }

    // Modal functions
    function openEventModal(event = null) {
        const modalTitle = document.getElementById('modal-title');
        const eventTitle = document.getElementById('event-title');
        const eventDate = document.getElementById('event-date');
        const eventTime = document.getElementById('event-time');
        const eventDuration = document.getElementById('event-duration');
        const eventDescription = document.getElementById('event-description');
        const eventReminder = document.getElementById('event-reminder');
        const eventInvitees = document.getElementById('event-invitees');

        if (event) {
            // Edit mode
            modalTitle.textContent = 'Edit Event';
            eventTitle.value = event.title;
            eventDate.value = event.date;
            eventTime.value = event.time;
            eventDuration.value = event.duration;
            eventDescription.value = event.description;
            eventReminder.value = event.reminder;
            eventInvitees.value = event.invitees.join(', ');

            // Store event ID for updating
            eventForm.dataset.eventId = event.id;
        } else {
            // Add mode
            modalTitle.textContent = 'Add New Event';
            eventTitle.value = '';
            eventDate.value = formatDate(currentDate);
            eventTime.value = '09:00';
            eventDuration.value = '60';
            eventDescription.value = '';
            eventReminder.value = 'none';
            eventInvitees.value = '';

            // Remove event ID if present
            if (eventForm.dataset.eventId) {
                delete eventForm.dataset.eventId;
            }
        }

        eventModal.classList.add('active');
    }

    function closeEventModal() {
        eventModal.classList.remove('active');
    }

    function openInvitationModal(invitation) {
        const invitationDetails = document.getElementById('invitation-details');

        invitationDetails.innerHTML = `
            <p><strong>${invitation.title}</strong></p>
            <p>From: ${invitation.sender}</p>
            <p>Date: ${new Date(`${invitation.date}T${invitation.time}`).toLocaleString()}</p>
            <p>Duration: ${invitation.duration} minutes</p>
            <p>${invitation.description}</p>
        `;

        // Store invitation ID for handling response
        invitationModal.dataset.invitationId = invitation.id;

        invitationModal.classList.add('active');
    }

    function closeInvitationModal() {
        invitationModal.classList.remove('active');
    }

    // View switching
    function switchToView(view) {
        currentView = view;

        // Update active button
        dayViewBtn.classList.remove('active');
        monthViewBtn.classList.remove('active');
        listViewBtn.classList.remove('active');

        // Hide all views
        dayViewContainer.classList.remove('active');
        monthViewContainer.classList.remove('active');
        listViewContainer.classList.remove('active');

        // Show selected view
        if (view === 'day') {
            dayViewBtn.classList.add('active');
            dayViewContainer.classList.add('active');
            renderDayView();
        } else if (view === 'month') {
            monthViewBtn.classList.add('active');
            monthViewContainer.classList.add('active');
            renderMonthView();
        } else if (view === 'list') {
            listViewBtn.classList.add('active');
            listViewContainer.classList.add('active');
            renderListView();
        }
    }

    // Event handlers
    dayViewBtn.addEventListener('click', () => switchToView('day'));
    monthViewBtn.addEventListener('click', () => switchToView('month'));
    listViewBtn.addEventListener('click', () => switchToView('list'));

    prevDayBtn.addEventListener('click', () => {
        currentDate.setDate(currentDate.getDate() - 1);
        renderDayView();
    });

    nextDayBtn.addEventListener('click', () => {
        currentDate.setDate(currentDate.getDate() + 1);
        renderDayView();
    });

    prevMonthBtn.addEventListener('click', () => {
        currentMonth--;
        if (currentMonth < 0) {
            currentMonth = 11;
            currentYear--;
        }
        renderMonthView();
    });

    nextMonthBtn.addEventListener('click', () => {
        currentMonth++;
        if (currentMonth > 11) {
            currentMonth = 0;
            currentYear++;
        }
        renderMonthView();
    });

    // إضافة حدث النقر على زر الإضافة في الكود الرئيسي
    addEventBtn.addEventListener('click', function(e) {
        e.preventDefault();
        console.log('تم النقر على زر الإضافة الرئيسي');
        eventModal.classList.add('active');
        document.body.style.overflow = 'hidden';
    });

    eventForm.addEventListener('submit', function(e) {
        e.preventDefault();

        const eventId = this.dataset.eventId;
        const eventData = {
            id: eventId ? parseInt(eventId) : events.length > 0 ? Math.max(...events.map(e => e.id)) + 1 : 1,
            title: document.getElementById('event-title').value,
            date: document.getElementById('event-date').value,
            time: document.getElementById('event-time').value,
            duration: parseInt(document.getElementById('event-duration').value),
            description: document.getElementById('event-description').value,
            reminder: document.getElementById('event-reminder').value,
            invitees: document.getElementById('event-invitees').value
                ? document.getElementById('event-invitees').value.split(',').map(email => email.trim())
                : [],
            accepted: [],
            declined: []
        };

        if (eventId) {
            // Update existing event
            const index = events.findIndex(e => e.id === parseInt(eventId));
            if (index !== -1) {
                // Preserve accepted/declined statuses
                eventData.accepted = events[index].accepted;
                eventData.declined = events[index].declined;
                events[index] = eventData;
            }
        } else {
            // Add new event
            events.push(eventData);
        }

        // Re-render views
        renderDayView();
        renderMonthView();
        renderListView();
        renderUpcomingEvents();

        closeEventModal();
    });

    cancelEventBtn.addEventListener('click', closeEventModal);

    closeModalBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            if (eventModal.classList.contains('active')) {
                closeEventModal();
            } else if (invitationModal.classList.contains('active')) {
                closeInvitationModal();
            }
        });
    });

    // إغلاق النموذج عند النقر خارج المحتوى
    window.addEventListener('click', function(event) {
        if (event.target === eventModal) {
            closeEventModal();
        }
    });

    acceptInvitationBtn.addEventListener('click', function() {
        const invitationId = parseInt(invitationModal.dataset.invitationId);
        const invitation = invitations.find(i => i.id === invitationId);

        if (invitation) {
            // Add to events with accepted status
            const newEvent = {
                id: invitation.eventId,
                title: invitation.title,
                date: invitation.date,
                time: invitation.time,
                duration: invitation.duration,
                description: invitation.description,
                reminder: 'none',
                invitees: [],
                accepted: [],
                declined: []
            };

            events.push(newEvent);

            // Remove the invitation
            invitations = invitations.filter(i => i.id !== invitationId);

            // Re-render views
            renderDayView();
            renderMonthView();
            renderListView();
            renderUpcomingEvents();
        }

        closeInvitationModal();

        // Check if there are more invitations
        checkForInvitations();
    });

    declineInvitationBtn.addEventListener('click', function() {
        const invitationId = parseInt(invitationModal.dataset.invitationId);

        // Simply remove the invitation
        invitations = invitations.filter(i => i.id !== invitationId);

        closeInvitationModal();

        // Check if there are more invitations
        checkForInvitations();
    });

    // Initialize the app
    initializeSampleData();
    switchToView('day');
    renderUpcomingEvents();

    // Check for invitations after a short delay (simulating real-world scenario)
    setTimeout(checkForInvitations, 1000);
});